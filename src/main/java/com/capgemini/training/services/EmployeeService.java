package com.capgemini.training.services;

import com.capgemini.training.exceptions.ValidationException;
import com.capgemini.training.models.Employee;
import com.capgemini.training.models.Level;
import com.capgemini.training.models.Unit;
import com.capgemini.training.repositories.EmployeeRepository;
import com.capgemini.training.repositories.UnitRepository;
import com.capgemini.training.rules.LevelRule;
import com.capgemini.training.rules.RuleEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;


@Service
public class EmployeeService implements GenericCRUDService<Employee, UUID> {
    @Autowired
    public EmployeeRepository employeeRepository;
    @Autowired
    public UnitRepository unitRepository;
    @Autowired
    public RuleEngine ruleEngine;

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

        //Check rule
        checkGradeCounselorCounselee(counselor.get().getLevel(),employee.getLevel());

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
    public Employee udpate(Employee employee, UUID uuid) throws ValidationException {
        //Check if employee exist
        Optional<Employee> employeeDb = getById(uuid);
        if (employeeDb.isEmpty()) {
            throw new ValidationException("Employee does not exist");
        }
        //Give id to new entity
        employee.setId(uuid);
        //Check if unit exist
        Optional<Unit> unitDb = unitRepository.findById(employee.getUnitId());
        if (unitDb.isEmpty()) {
            throw new ValidationException("Unit does not exist");
        }
        employee.setUnit(unitDb.get());
        //Check if employee has a previous counselor
        Employee previousCounselor = employeeDb.get().getCounselor();
        if (previousCounselor != null && !employee.getCounselorId().equals(previousCounselor.getId())) {
            //Remove conselee from previous counselor
            Set<Employee> previouscounselees = previousCounselor.getCounselees();
            previouscounselees.remove(employeeDb.get());
            // update previous counselor data
            employeeRepository.save(previousCounselor);
        }
        //Check if employee has new counselor or not
        if (employee.getCounselorId() == null) {
            employeeRepository.save(employee);
            return employee;
        }

        //Check if new counselor exist
        Optional<Employee> newCounselor = getById(employee.getCounselorId());
        if (newCounselor.isEmpty()) {
            throw new ValidationException("Counselor does not exist");
        }

        //Check rules
        checkGradeCounselorCounselee(newCounselor.get().getLevel(),employee.getLevel());

        //set employee the new counselor
        employee.setCounselor(newCounselor.get());
        //Add employee to counselor
        Set<Employee> counselees = newCounselor.get().getCounselees();
        counselees.add(employee);
        newCounselor.get().setCounselees(counselees);
        //save entities
        employeeRepository.save(employee);
        employeeRepository.save(newCounselor.get());

        return employee;
    }

    public void checkGradeCounselorCounselee(Level counselorLevel, Level employeeLevel) throws ValidationException {
        //Create the needed rules
        LevelRule levelRule = new LevelRule();
        //Add the rule
        ruleEngine.addRule(levelRule);
        //Make the input value
        Pair<Level,Level> counselorEmployeeLevels = Pair.of(counselorLevel,employeeLevel);
        //check validation of rules
        if(!ruleEngine.validate(counselorEmployeeLevels)){
           throw new ValidationException("Is not valid due the level of the employees");
        }
    }

    @Override
    public Optional<Employee> getById(UUID id) {
        return employeeRepository.findById(id);
    }


}
