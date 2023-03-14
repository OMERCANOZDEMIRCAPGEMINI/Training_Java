package com.capgemini.training.repositories;

import com.capgemini.training.models.Employee;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface EmployeeRepository extends CrudRepository<Employee, UUID> {
}
