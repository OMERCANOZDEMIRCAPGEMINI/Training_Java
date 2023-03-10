package com.capgemini.training.services;

import com.capgemini.training.exceptions.PersonCannotBeCreatedException;
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
    public Person create(Person person, UUID counselorId) throws PersonCannotBeCreatedException {
        if (counselorId != null) {
            Optional<Person> counselor = getPersonById(counselorId);
            if (counselor.isPresent()) {
                if (counselor.get().getLevel().compareTo(person.getLevel()) > 0) {
                    person.setCounselor(counselor.get());
                } else {
                    throw new PersonCannotBeCreatedException("Person cannot be created due to the level of the counselor");
                }
            } else {
                throw new PersonCannotBeCreatedException("Person cannot be created because counselor does not exist");
            }
        }
        return personRepository.save(person);
    }


    @Override
    public Optional<Person> getPersonById(UUID id) {
        return personRepository.findById(id);
    }
}
