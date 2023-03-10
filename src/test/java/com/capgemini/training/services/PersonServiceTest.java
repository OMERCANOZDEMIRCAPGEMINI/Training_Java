package com.capgemini.training.services;

import com.capgemini.training.exceptions.PersonCannotBeCreatedException;
import com.capgemini.training.models.Level;
import com.capgemini.training.models.Person;
import com.capgemini.training.repositories.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {
    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonService personService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void shouldGetAllPeople() {
        // Arrange
        List<Person> people = new ArrayList<Person>();
        people.add(new Person());
        people.add(new Person());

        // Act
        when(personRepository.findAll()).thenReturn(people);
        Iterable<Person> result = personService.getAllPersons();

        // Assert
        assertEquals(people, result);
    }

    @Test
    void ShouldCreatePersonWithoutCounselor() throws PersonCannotBeCreatedException {
        // Arrange
        Person person = new Person();
        person.setFirstname("omer");
        person.setLastname("ozdemir");

        // Act
        when(personRepository.save(person)).thenReturn(person);
        Person created_person = personService.create(person,null);
        // Assert
        assertEquals(person, created_person);
    }

    @Test
    void getPersonById() {
        // Arrange
        UUID uuid = UUID.randomUUID();
        Person person = new Person();
        person.setId(uuid);
        person.setFirstname("Omer");
        person.setLastname("Ozdemir");
        person.setLevel(Level.A3);

        // Act
        when(personRepository.findById(uuid)).thenReturn(Optional.of(person));
        Person personDb = personService.getPersonById(uuid).get();

        // assert
        assertEquals(person,personDb);
    }
}