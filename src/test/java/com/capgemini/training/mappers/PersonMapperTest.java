package com.capgemini.training.mappers;


import com.capgemini.training.dtos.PersonDTO;
import com.capgemini.training.models.Level;
import com.capgemini.training.models.Person;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Date;
import java.util.UUID;

public class PersonMapperTest {
    @Test
    void shouldMapPerson(){
        // Arrange
        Person person = new Person();
        person.setFirstname("Omer");
        person.setLastname("Ozdemir");
        person.setBirthDate(new Date("05/07/2000"));
        person.setStartDate(new Date("06/02/2023"));
        person.setLevel(Level.A3);

        // Act
        PersonDTO mappedPerson = PersonMapper.INSTANCE.personToPersonDto(person);

        //Assert
        assertNotNull(mappedPerson);
        assertEquals(person.getFirstname(),mappedPerson.getFirstname());
        assertEquals(person.getLastname(),mappedPerson.getLastname());
        assertEquals(person.getLevel(),mappedPerson.getLevel());
        assertEquals(person.getBirthDate(),mappedPerson.getBirthDate());
        assertEquals(person.getStartDate(),mappedPerson.getStartDate());
    }
}
