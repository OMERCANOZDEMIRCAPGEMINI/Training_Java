package com.capgemini.training.repositories;

import com.capgemini.training.models.Person;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface PersonRepository extends CrudRepository<Person, UUID> {
}
