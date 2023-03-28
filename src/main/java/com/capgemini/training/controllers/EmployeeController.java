package com.capgemini.training.controllers;

import com.capgemini.training.dtos.get.EmployeeGetDTO;
import com.capgemini.training.dtos.post.EmployeePostDTO;
import com.capgemini.training.exceptions.ValidationException;
import com.capgemini.training.mappers.EmployeeMapper;
import com.capgemini.training.models.Employee;
import com.capgemini.training.services.EmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "employees")
@Api(tags = "Employees controller")
public class EmployeeController {

    @Autowired
    public EmployeeService employeeService;

    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get all employees from database")
    public ResponseEntity<Iterable<EmployeeGetDTO>> getAll() throws Exception {
        try {
            Iterable<EmployeeGetDTO> employees = EmployeeMapper.INSTANCE.employeesListToEmployeesGetListDto(employeeService.getAll());
            return ResponseEntity.ok(employees);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new Exception("Something went wrong");
        }
    }

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get employee by UUID from database")
    public ResponseEntity<?> getById(@PathVariable(value = "id") UUID id) throws Exception {
        try {

            Optional<Employee> employee = employeeService.getById(id);
            if (employee.isPresent()) {
                EmployeeGetDTO responseEmployee = EmployeeMapper.INSTANCE.employeeToEmployeeGetDto(employee.get());
                return ResponseEntity.ok(responseEmployee);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found");
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new Exception("Something went wrong");
        }
    }


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Create an employee")
    public ResponseEntity<EmployeeGetDTO> create(@Valid @RequestBody EmployeePostDTO employeePostDTO, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            String errorMessage = String.join(",", errors);
            throw new ValidationException(errorMessage);
        }
        try {
            Employee createdEmployee = employeeService.create(EmployeeMapper.INSTANCE.employeeDtoToEmployee(employeePostDTO));
            EmployeeGetDTO responseEmployee = EmployeeMapper.INSTANCE.employeeToEmployeeGetDto(createdEmployee);
            return ResponseEntity.ok(responseEmployee);
        } catch (ValidationException e) {
            logger.error(e.getErrors().toString());
            throw new ValidationException(e.getErrors());
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new Exception("Something went wrong");
        }
    }

    @PutMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update an employee")
    public ResponseEntity<EmployeeGetDTO> update(@PathVariable(value = "id") UUID id, @Valid @RequestBody EmployeePostDTO employee) throws Exception {
        try {
            Employee updatedEmployee = employeeService.update(EmployeeMapper.INSTANCE.employeeDtoToEmployee(employee), id);
            EmployeeGetDTO responseEmployee = EmployeeMapper.INSTANCE.employeeToEmployeeGetDto(updatedEmployee);
            return ResponseEntity.ok(responseEmployee);
        } catch (ValidationException e) {
            logger.error(e.getErrors().toString());
            throw new ValidationException(e.getErrors());
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new Exception("Something went wrong");
        }
    }
}
