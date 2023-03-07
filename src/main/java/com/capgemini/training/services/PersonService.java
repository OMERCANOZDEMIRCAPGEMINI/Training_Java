package com.capgemini.training.services;

import com.capgemini.training.models.Person;
import com.capgemini.training.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class PersonService implements IPersonService {
    @Autowired
    public PersonRepository personRepository;

    @Override
    public Iterable<Person> getAllPersons() {
        return personRepository.findAll();
    }

    @Override
    public Person create(Person person) {
        return personRepository.save(person);
    }

    @Override
    public Optional<Person> getPersonById(UUID id) {
        return personRepository.findById(id);
    }
}
