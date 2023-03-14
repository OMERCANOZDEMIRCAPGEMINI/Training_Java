package com.capgemini.training.services;

import com.capgemini.training.exceptions.PersonCannotBeCreatedException;

import java.util.List;
import java.util.Optional;

public interface GenericCRUDService <T, ID> {
    Iterable<T> getAll();
    Optional<T> getById(ID id);
    T create(T entity) throws PersonCannotBeCreatedException;
}
