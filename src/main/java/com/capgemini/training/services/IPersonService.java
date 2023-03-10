package com.capgemini.training.services;


import com.capgemini.training.exceptions.PersonCannotBeCreatedException;
import com.capgemini.training.models.Person;

import java.util.Optional;
import java.util.UUID;

public interface IPersonService {
    Iterable<Person> getAllPersons();
    Person create(Person person,UUID counselorId) throws PersonCannotBeCreatedException;
    Optional<Person> getPersonById(UUID id);
}
