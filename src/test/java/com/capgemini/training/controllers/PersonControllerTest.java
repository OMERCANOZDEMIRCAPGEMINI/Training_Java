package com.capgemini.training.controllers;

import com.capgemini.training.dtos.PersonDTO;
import com.capgemini.training.exceptions.PersonCannotBeCreatedException;
import com.capgemini.training.loggers.ILogger;
import com.capgemini.training.models.Person;
import com.capgemini.training.services.IPersonService;
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
    void shouldCreatePersonWithoutCounselor() throws PersonCannotBeCreatedException {
        // Arrange
        PersonDTO personDto = new PersonDTO();
        personDto.setLastname("Ozdemir");
        personDto.setFirstname("Omer");
        personDto.setCounselorId(null);

        Person person = new Person();
        person.setLastname("Ozdemir");
        person.setFirstname("Omer");
        person.setCounselor(null);

        // Act
        when(personService.create(any(Person.class), eq(null))).thenReturn(person);

        PersonDTO createdPerson = (PersonDTO) controller.create(personDto, bindingResult).getBody();

        //assert
        assertNotNull(createdPerson);
        assertEquals(personDto.getFirstname(), createdPerson.getFirstname());
        assertEquals(personDto.getLastname(), createdPerson.getLastname());
    }

    @Test
    void shouldGetExceptionWhenCreatingPerson() throws PersonCannotBeCreatedException {
        // Arrange
        PersonDTO personDTO = new PersonDTO();
        personDTO.setLastname("Ozdemir");
        personDTO.setFirstname("Omer");

        // Act
        when(personService.create(any(Person.class), eq(null))).thenThrow(new RuntimeException("Runtime exception"));

        ResponseEntity<?> response = controller.create(personDTO, bindingResult);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        verify(logger).log("Runtime exception");
    }
    @Test
    void shouldThrowPersonCannotBeCreatedExceptionWhenCreatingPerson() throws PersonCannotBeCreatedException {
        // Arrange
        PersonDTO personDTO = new PersonDTO();
        // Act
        when(personService.create(any(Person.class), eq(null))).thenThrow(new PersonCannotBeCreatedException("Person cannot be created"));

        ResponseEntity<?> response = controller.create(personDTO, bindingResult);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Person cannot be created",response.getBody());
    }
}