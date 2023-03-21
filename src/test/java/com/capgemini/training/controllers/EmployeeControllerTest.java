package com.capgemini.training.controllers;

import com.capgemini.training.dtos.ResponseDTO;
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
    void shouldGetAllPeople() {
        // Arrange
        List<Employee> employees = new ArrayList<Employee>();
        employees.add(new Employee());
        employees.add(new Employee());

        // Act
        when(employeeService.getAll()).thenReturn(employees);

        // Assert
        Iterable<EmployeeGetDTO> result = controller.getAll().getBody().getResponseObject();
        assertNotNull(result);
    }

    @Test
    void shouldGetExceptionByCallingAllPeople() {
        // Act
        when(employeeService.getAll()).thenThrow(new RuntimeException("Error when fetching people"));
        ResponseEntity<ResponseDTO<Iterable<EmployeeGetDTO>>> response = controller.getAll();
        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
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
        when(employeeService.getById(id)).thenReturn(Optional.of(employee));
        EmployeeGetDTO employeeDb = controller.getById(id).getBody().getResponseObject();

        // Assert
        assertEquals(employee.getFirstname(), employeeDb.getFirstname());
    }

    @Test
    void shouldGetNotFoundByCallingPersonById() {
        // Arrange
        UUID id = UUID.randomUUID();
        // Act
        when(employeeService.getById(any(UUID.class))).thenReturn(Optional.empty());
        ResponseEntity<ResponseDTO<EmployeeGetDTO>> response = controller.getById(id);
        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Person not found", response.getBody().getError());
    }

    @Test
    void shouldGetExceptionByCallingPersonById() {
        // Arrange
        UUID uuid = UUID.randomUUID();
        // Act
        when(employeeService.getById(any(UUID.class))).thenThrow(new RuntimeException("Error when fetching person"));
        ResponseEntity<?> response = controller.getById(uuid);
        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void shouldCreatePersonWithoutCounselor() throws ValidationException {
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

        ResponseEntity<ResponseDTO<EmployeeGetDTO>> createdPerson =  controller.create(EMPLOYEEDTO, bindingResult);

        //assert
        assertNotNull(createdPerson);
        assertEquals(EMPLOYEEDTO.getFirstname(), createdPerson.getBody().getResponseObject().getFirstname());
        assertEquals(EMPLOYEEDTO.getLastname(), createdPerson.getBody().getResponseObject().getLastname());
    }

    @Test
    void shouldGetExceptionWhenCreatingPerson() throws ValidationException {
        // Arrange
        EmployeePostDTO EMPLOYEEDTO = new EmployeePostDTO();
        EMPLOYEEDTO.setLastname("Ozdemir");
        EMPLOYEEDTO.setFirstname("Omer");

        // Act
        when(employeeService.create(any(Employee.class))).thenThrow(new RuntimeException("Runtime exception"));

        ResponseEntity<?> response = controller.create(EMPLOYEEDTO, bindingResult);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
    @Test
    void shouldThrowPersonCannotBeCreatedExceptionWhenCreatingPerson() throws ValidationException {
        // Arrange
        EmployeePostDTO EMPLOYEEDTO = new EmployeePostDTO();
        // Act
        when(employeeService.create(any(Employee.class))).thenThrow(new ValidationException("Person cannot be created"));

        ResponseEntity<ResponseDTO<EmployeeGetDTO>> response = controller.create(EMPLOYEEDTO, bindingResult);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Person cannot be created",response.getBody().getError());
    }
}