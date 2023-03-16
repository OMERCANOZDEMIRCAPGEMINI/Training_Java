package com.capgemini.training.services;

import com.capgemini.training.exceptions.ValidationException;
import com.capgemini.training.models.Employee;
import com.capgemini.training.models.Unit;
import com.capgemini.training.repositories.EmployeeRepository;
import com.capgemini.training.repositories.UnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static com.capgemini.training.helpers.GradeChecker.checkGradeCounselorConselee;

@Service
public class EmployeeService implements GenericCRUDService<Employee, UUID> {
    @Autowired
    public EmployeeRepository employeeRepository;
    @Autowired
    public UnitRepository unitRepository;

    @Override
    public Iterable<Employee> getAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee create(Employee employee) throws ValidationException {

        // Check unit
        Optional<Unit> unit = unitRepository.findById(employee.getUnitId());
        if (unit.isPresent()) {
            employee.setUnit(unit.get());
        } else {
            throw new ValidationException("Unit does not exist");
        }

        // Check if counselor id is null or not
        if (employee.getCounselorId() == null) {
            return employeeRepository.save(employee);
        }

        Optional<Employee> counselor = getById(employee.getCounselorId());
        if (counselor.isEmpty()) {
            throw new ValidationException("Person cannot be created because counselor does not exist");
        }

        if (!checkGradeCounselorConselee(counselor.get().getLevel(), employee.getLevel())) {
            throw new ValidationException("Person cannot be created due to the level of the counselor");
        }

        // update counselor and counselees
        Set<Employee> counselees = counselor.get().getCounselees();
        counselees.add(employee);
        counselor.get().setCounselees(counselees);
        employee.setCounselor(counselor.get());

        // save the entities
        employeeRepository.save(employee);
        employeeRepository.save(counselor.get());

        return employee;
    }


    @Override
    public Optional<Employee> getById(UUID id) {
        return employeeRepository.findById(id);
    }

    public Employee updateCounselorFromEmployee(UUID employeeId, UUID counselorId) throws ValidationException {

        // Check if both exists
        Optional<Employee> employee = getById(employeeId);
        Optional<Employee> counselor = getById(counselorId);

        if (counselor.isEmpty() || employee.isEmpty()) {
            throw new ValidationException("Employee cannot be updated because counselor or employee does not exist");
        }
        if (!checkGradeCounselorConselee(counselor.get().getLevel(), employee.get().getLevel())) {
            throw new ValidationException("Person cannot be created due to the level of the counselor");
        }
        //Get previous counselor
        Employee previousCounselor = employee.get().getCounselor();
        if (previousCounselor != null) {
            //Remove conselee from previous counselor
            Set<Employee> previouscounselees = previousCounselor.getCounselees();
            previouscounselees.remove(employee.get());
            // update
            employeeRepository.save(previousCounselor);
        }

        //Add employee to counselor
        Set<Employee> counselees = counselor.get().getCounselees();
        counselees.add(employee.get());
        counselor.get().setCounselees(counselees);
        //Set new counselor to employee
        employee.get().setCounselor(counselor.get());

        // save the entities
        employeeRepository.save(employee.get());
        employeeRepository.save(counselor.get());

        return employee.get();
    }

}
