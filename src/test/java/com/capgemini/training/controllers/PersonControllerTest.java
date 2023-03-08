package com.capgemini.training.controllers;

import com.capgemini.training.loggers.ILogger;
import com.capgemini.training.models.Person;
import com.capgemini.training.services.IPersonService;
import org.h2.tools.GUIConsole;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonControllerTest {
    @Mock
    private IPersonService personService;

    @Mock
    private ILogger logger;
    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private PersonController controller;

    @Test
    void shouldGetAllPeople() {
        // Arrange
        List<Person> persons = new ArrayList<Person>();
        persons.add(new Person());
        persons.add(new Person());

        // Act
        when(personService.getAllPersons()).thenReturn(persons);

        // Assert
        Iterable<Person> result = (Iterable<Person>) controller.getAll().getBody();
        assertEquals(persons, result);
    }

    @Test
    void shouldGetExceptionByCallingAllPeople() {
        // Act
        when(personService.getAllPersons()).thenThrow(new RuntimeException("Error when fetching people"));
        ResponseEntity<?> response = controller.getAll();
        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        verify(logger).log("Error when fetching people");
    }

    @Test
    void shouldGetPersonById() {
        // Arrange
        UUID id = UUID.randomUUID();
        Person person = new Person();
        person.setFirstname("omer");
        person.setLastname("Ozdemir");
        person.setId(id);

        // Act
        when(personService.getPersonById(id)).thenReturn(Optional.of(person));
        Person personDb = (Person) controller.getById(id).getBody();

        // Assert
        assertEquals(person, personDb);
    }

    @Test
    void shouldGetNotFoundByCallingPersonById() {
        // Arrange
        UUID id = UUID.randomUUID();
        // Act
        when(personService.getPersonById(any(UUID.class))).thenReturn(Optional.empty());
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
        when(personService.getPersonById(any(UUID.class))).thenThrow(new RuntimeException("Error when fetching person"));
        ResponseEntity<?> response = controller.getById(uuid);
        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        verify(logger).log("Error when fetching person");
    }

    @Test
    void shouldCreatePerson() {
        // Arrange
        Person person = new Person();
        person.setLastname("Ozdemir");
        person.setFirstname("Omer");

        // Act
        when(personService.create(person)).thenReturn(person);
        Person createdPerson = (Person) controller.create(person,bindingResult).getBody();

        //assert
        assertEquals(person,createdPerson);
    }
    @Test
    void shouldGetExceptionWhenCreatingPerson() {
        // Arrange
        Person person = new Person();
        person.setLastname("Ozdemir");
        person.setFirstname("Omer");

        // Act
        when(personService.create(person)).thenThrow(new RuntimeException("Unable to create a person"));
        ResponseEntity<?> response = controller.create(person,bindingResult);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        verify(logger).log("Unable to create a person");
    }
}