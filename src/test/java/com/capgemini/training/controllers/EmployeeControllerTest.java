package com.capgemini.training.controllers;

import com.capgemini.training.dtos.EmployeeDTO;
import com.capgemini.training.exceptions.PersonCannotBeCreatedException;
import com.capgemini.training.loggers.ILogger;
import com.capgemini.training.models.Employee;
import com.capgemini.training.services.PersonService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.Mapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Mapper(componentModel = "spring")
class EmployeeControllerTest {
    @Mock
    private PersonService personService;

    @Mock
    private ILogger logger;
    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private EmployeeController controller;

    @Test
    void shouldGetAllPeople() {
        // Arrange
        List<Employee> employees = new ArrayList<Employee>();
        employees.add(new Employee());
        employees.add(new Employee());

        // Act
        when(personService.getAll()).thenReturn(employees);

        // Assert
        Iterable<Employee> result = (Iterable<Employee>) controller.getAll().getBody();
        assertEquals(employees, result);
    }

    @Test
    void shouldGetExceptionByCallingAllPeople() {
        // Act
        when(personService.getAll()).thenThrow(new RuntimeException("Error when fetching people"));
        ResponseEntity<?> response = controller.getAll();
        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        verify(logger).log("Error when fetching people");
    }

    @Test
    void shouldGetPersonById() {
        // Arrange
        UUID id = UUID.randomUUID();
        Employee employee = new Employee();
        employee.setFirstname("omer");
        employee.setLastname("Ozdemir");
        employee.setId(id);

        // Act
        when(personService.getById(id)).thenReturn(Optional.of(employee));
        Employee employeeDb = (Employee) controller.getById(id).getBody();

        // Assert
        assertEquals(employee, employeeDb);
    }

    @Test
    void shouldGetNotFoundByCallingPersonById() {
        // Arrange
        UUID id = UUID.randomUUID();
        // Act
        when(personService.getById(any(UUID.class))).thenReturn(Optional.empty());
        ResponseEntity<?> response = controller.getById(id);
        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Person not found", response.getBody());
    }

    @Test
    void shouldGetExceptionByCallingPersonById() {
        // Arrange
        UUID uuid = UUID.randomUUID();
        // Act
        when(personService.getById(any(UUID.class))).thenThrow(new RuntimeException("Error when fetching person"));
        ResponseEntity<?> response = controller.getById(uuid);
        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        verify(logger).log("Error when fetching person");
    }

    @Test
    void shouldCreatePersonWithoutCounselor() throws PersonCannotBeCreatedException {
        // Arrange
        EmployeeDTO EMPLOYEEDTO = new EmployeeDTO();
        EMPLOYEEDTO.setLastname("Ozdemir");
        EMPLOYEEDTO.setFirstname("Omer");
        EMPLOYEEDTO.setCounselorId(null);

        Employee employee = new Employee();
        employee.setLastname("Ozdemir");
        employee.setFirstname("Omer");
        employee.setCounselor(null);

        // Act
        when(personService.create(any(Employee.class))).thenReturn(employee);

        EmployeeDTO createdPerson = (EmployeeDTO) controller.create(EMPLOYEEDTO, bindingResult).getBody();

        //assert
        assertNotNull(createdPerson);
        assertEquals(EMPLOYEEDTO.getFirstname(), createdPerson.getFirstname());
        assertEquals(EMPLOYEEDTO.getLastname(), createdPerson.getLastname());
    }

    @Test
    void shouldGetExceptionWhenCreatingPerson() throws PersonCannotBeCreatedException {
        // Arrange
        EmployeeDTO EMPLOYEEDTO = new EmployeeDTO();
        EMPLOYEEDTO.setLastname("Ozdemir");
        EMPLOYEEDTO.setFirstname("Omer");

        // Act
        when(personService.create(any(Employee.class))).thenThrow(new RuntimeException("Runtime exception"));

        ResponseEntity<?> response = controller.create(EMPLOYEEDTO, bindingResult);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        verify(logger).log("Runtime exception");
    }
    @Test
    void shouldThrowPersonCannotBeCreatedExceptionWhenCreatingPerson() throws PersonCannotBeCreatedException {
        // Arrange
        EmployeeDTO EMPLOYEEDTO = new EmployeeDTO();
        // Act
        when(personService.create(any(Employee.class))).thenThrow(new PersonCannotBeCreatedException("Person cannot be created"));

        ResponseEntity<?> response = controller.create(EMPLOYEEDTO, bindingResult);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Person cannot be created",response.getBody());
    }
}