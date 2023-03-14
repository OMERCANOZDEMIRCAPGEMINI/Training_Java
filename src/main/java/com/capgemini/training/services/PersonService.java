package com.capgemini.training.services;

import com.capgemini.training.exceptions.PersonCannotBeCreatedException;
import com.capgemini.training.models.Employee;
import com.capgemini.training.models.Level;
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
public class PersonService implements GenericCRUDService<Employee, UUID> {
    @Autowired
    public EmployeeRepository employeeRepository;
    @Autowired
    public UnitRepository unitRepository;

    @Override
    public Iterable<Employee> getAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee create(Employee employee) throws PersonCannotBeCreatedException {

        // Check unit
        Optional<Unit> unit = unitRepository.findById(employee.getUnitId());
        if (unit.isPresent()) {
            employee.setUnit(unit.get());
        } else {
            throw new PersonCannotBeCreatedException("Unit does not exist");
        }

        // Check if counselor id is null or not
        if (employee.getCounselorId() == null) {
            return employeeRepository.save(employee);
        }

        Optional<Employee> counselor = getById(employee.getCounselorId());
        if (counselor.isEmpty()) {
            throw new PersonCannotBeCreatedException("Person cannot be created because counselor does not exist");
        }

        if (!checkGradeCounselorConselee(counselor.get().getLevel(), employee.getLevel())) {
            throw new PersonCannotBeCreatedException("Person cannot be created due to the level of the counselor");
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


}
