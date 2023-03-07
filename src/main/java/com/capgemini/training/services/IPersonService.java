package com.capgemini.training.services;


import com.capgemini.training.models.Person;

import java.util.Optional;
import java.util.UUID;

public interface IPersonService {
    Iterable<Person> getAllPersons();
    Person create(Person person);
    Optional<Person> getPersonById(UUID id);
}
