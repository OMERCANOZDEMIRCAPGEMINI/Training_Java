package com.capgemini.training.controllers;

import com.capgemini.training.dtos.get.EmployeeGetDTO;
import com.capgemini.training.dtos.post.EmployeePostDTO;
import com.capgemini.training.exceptions.ValidationException;
import com.capgemini.training.models.Employee;
import com.capgemini.training.services.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.Mapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
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
    private EmployeeService employeeService;

    @Mock
    private Logger logger;
    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private EmployeeController controller;

    @Test
    void shouldGetAllPeople() throws Exception {
        // Arrange
        List<Employee> employees = new ArrayList<Employee>();
        employees.add(new Employee());
        employees.add(new Employee());

        // Act
        when(employeeService.getAll()).thenReturn(employees);

        // Assert
        Iterable<EmployeeGetDTO> result = (Iterable<EmployeeGetDTO>) controller.getAll().getBody();
        assertNotNull(result);
    }

    @Test
    void shouldGetExceptionByCallingAllPeople() throws Exception {
        // Act
        when(employeeService.getAll()).thenThrow(new RuntimeException("Something went wrong"));
        // Assert
        assertThrows(Exception.class, () -> controller.getAll());
    }

    @Test
    void shouldGetPersonById() throws Exception {
        // Arrange
        UUID id = UUID.randomUUID();
        Employee employee = new Employee();
        employee.setFirstname("omer");
        employee.setLastname("Ozdemir");
        employee.setId(id);

        // Act
        when(employeeService.getById(id)).thenReturn(Optional.of(employee));
        EmployeeGetDTO employeeDb = (EmployeeGetDTO) controller.getById(id).getBody();

        // Assert
        assertEquals(employee.getFirstname(), employeeDb.getFirstname());
    }

    @Test
    void shouldGetNotFoundByCallingPersonById() throws Exception {
        // Arrange
        UUID id = UUID.randomUUID();
        // Act
        when(employeeService.getById(any(UUID.class))).thenReturn(Optional.empty());
        ResponseEntity<EmployeeGetDTO> response = (ResponseEntity<EmployeeGetDTO>) controller.getById(id);
        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void shouldGetExceptionByCallingPersonById() throws Exception {
        // Arrange
        UUID uuid = UUID.randomUUID();
        // Act
        when(employeeService.getById(any(UUID.class))).thenThrow(new RuntimeException("Error when fetching person"));
        // Assert
        assertThrows(Exception.class, () -> controller.getById(uuid));
    }

    @Test
    void shouldCreatePersonWithoutCounselor() throws Exception {
        // Arrange
        EmployeePostDTO EMPLOYEEDTO = new EmployeePostDTO();
        EMPLOYEEDTO.setLastname("Ozdemir");
        EMPLOYEEDTO.setFirstname("Omer");
        EMPLOYEEDTO.setCounselorId(null);

        Employee employee = new Employee();
        employee.setLastname("Ozdemir");
        employee.setFirstname("Omer");
        employee.setCounselor(null);

        // Act
        when(employeeService.create(any(Employee.class))).thenReturn(employee);

        ResponseEntity<EmployeeGetDTO> createdPerson = (ResponseEntity<EmployeeGetDTO>) controller.create(EMPLOYEEDTO, bindingResult);

        //assert
        assertNotNull(createdPerson);
        assertEquals(EMPLOYEEDTO.getFirstname(), createdPerson.getBody().getFirstname());
        assertEquals(EMPLOYEEDTO.getLastname(), createdPerson.getBody().getLastname());
    }

    @Test
    void shouldGetExceptionWhenCreatingPerson() throws Exception {
        // Arrange
        EmployeePostDTO EMPLOYEEDTO = new EmployeePostDTO();
        EMPLOYEEDTO.setLastname("Ozdemir");
        EMPLOYEEDTO.setFirstname("Omer");

        // Act
        when(employeeService.create(any(Employee.class))).thenThrow(new RuntimeException("Runtime exception"));

        // Assert
        assertThrows(Exception.class, () -> controller.create(EMPLOYEEDTO, bindingResult));
    }

    @Test
    void shouldThrowPersonCannotBeCreatedExceptionWhenCreatingPerson() throws Exception {
        // Arrange
        EmployeePostDTO EMPLOYEEDTO = new EmployeePostDTO();
        // Act
        when(employeeService.create(any(Employee.class))).thenThrow(new ValidationException("Employee cannot be created"));


        // Assert
        assertThrows(ValidationException.class, () -> controller.create(EMPLOYEEDTO, bindingResult));
    }
}