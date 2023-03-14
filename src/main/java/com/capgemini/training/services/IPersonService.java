package com.capgemini.training.services;


import com.capgemini.training.exceptions.PersonCannotBeCreatedException;
import com.capgemini.training.models.Employee;

import java.util.Optional;
import java.util.UUID;

public interface IPersonService {
    Iterable<Employee> getAllPersons();
    Employee create(Employee employee, UUID counselorId) throws PersonCannotBeCreatedException;
    Optional<Employee> getPersonById(UUID id);
}
