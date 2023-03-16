package com.capgemini.training.controllers;

import com.capgemini.training.dtos.get.EmployeeGetDTO;
import com.capgemini.training.dtos.post.EmployeePostDTO;
import com.capgemini.training.dtos.ResponseDTO;
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
    @ApiOperation(value = "Get all people from database")
    public ResponseEntity<ResponseDTO<Iterable<EmployeeGetDTO>>> getAll() {
        try {
            Iterable<EmployeeGetDTO> employees = EmployeeMapper.INSTANCE.employeesListToEmployeesGetListDto(employeeService.getAll());
            return ResponseEntity.ok(new ResponseDTO<>(employees));
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDTO<>("an error occurred"));
        }
    }

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get person by UUID from database")
    public ResponseEntity<ResponseDTO<EmployeeGetDTO>> getById(@PathVariable(value = "id") UUID id) {
        try {

            Optional<Employee> employee = employeeService.getById(id);
            if (employee.isPresent()) {
                EmployeeGetDTO responseEmployee = EmployeeMapper.INSTANCE.employeeToEmployeeGetDto(employee.get());
                return ResponseEntity.ok(new ResponseDTO<>(responseEmployee));
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDTO<>("Person not found"));
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDTO<>("an error occurred"));
        }
    }


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Create a person")
    public ResponseEntity<ResponseDTO<EmployeeGetDTO>> create(@Valid @RequestBody EmployeePostDTO employeePostDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO<>(errors));
        }
        try {
            Employee createdEmployee = employeeService.create(EmployeeMapper.INSTANCE.employeeDtoToEmployee(employeePostDTO));
            EmployeeGetDTO responseEmployee = EmployeeMapper.INSTANCE.employeeToEmployeeGetDto(createdEmployee);
            return ResponseEntity.ok(new ResponseDTO<>(responseEmployee));
        } catch (ValidationException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO<>(e.getMessage()));
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDTO<>("an error occurred"));
        }
    }
    @PutMapping(value = "{employeeId}/{counselorId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO<EmployeeGetDTO>> updateCounselorFromCounselee(@PathVariable(value = "employeeId") UUID employeeId,@PathVariable(value = "counselorId") UUID counselorId){
        try {
            Employee updatedEmployee = employeeService.updateCounselorFromEmployee(employeeId,counselorId);
            EmployeeGetDTO responseEmployee = EmployeeMapper.INSTANCE.employeeToEmployeeGetDto(updatedEmployee);
            return ResponseEntity.ok(new ResponseDTO<>(responseEmployee));
        } catch (ValidationException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO<>(e.getMessage()));
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDTO<>("an error occurred"));
        }
    }
}
