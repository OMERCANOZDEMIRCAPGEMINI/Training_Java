package com.capgemini.training.controllers;

import com.capgemini.training.dtos.EmployeeDTO;
import com.capgemini.training.exceptions.PersonCannotBeCreatedException;
import com.capgemini.training.mappers.PersonMapper;
import com.capgemini.training.models.Employee;
import com.capgemini.training.services.PersonService;
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
    public PersonService personService;

    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);



    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get all people from database")
    public ResponseEntity<?> getAll() {
        try {
            Iterable<Employee> people = personService.getAll();
            return ResponseEntity.ok(people);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("an error occurred");
        }
    }

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get person by UUID from database")
    public ResponseEntity<?> getById(@PathVariable(value = "id") UUID id) {
        try {

            Optional<Employee> person = personService.getById(id);
            if (person.isPresent()) {
                return ResponseEntity.ok(person.get());
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Person not found");
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("an error occurred");
        }
    }


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Create a person")
    public ResponseEntity<?> create(@Valid @RequestBody EmployeeDTO employeeDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }
        try {
            Employee createdEmployee = personService.create(PersonMapper.INSTANCE.personDtoToPerson(employeeDTO));
            EmployeeDTO responsePerson = PersonMapper.INSTANCE.personToPersonDto(createdEmployee);
            responsePerson.setCounselorId(employeeDTO.getCounselorId());

            return ResponseEntity.ok(responsePerson);
        } catch (PersonCannotBeCreatedException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("an error occurred");
        }
    }
}