package com.capgemini.training.controllers;

import com.capgemini.training.models.Person;
import com.capgemini.training.services.IPersonService;
import org.apache.catalina.filters.ExpiresFilter;
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
@RequestMapping(value = "persons")

public class PersonController {
    @Autowired
    public IPersonService personService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAll() {
        try {
            Iterable<Person> persons = personService.getAllPersons();
            return ResponseEntity.ok(persons);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    @GetMapping(value = "{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getById(@PathVariable(value = "id")UUID id) {
        try {
            Optional<Person> person = personService.getPersonById(id);
            return ResponseEntity.ok(person);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@Valid @RequestBody Person person, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }
        try {
            Person createdPerson = personService.create(person);
            return ResponseEntity.ok(createdPerson);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
