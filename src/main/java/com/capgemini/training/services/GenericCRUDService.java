package com.capgemini.training.services;

import com.capgemini.training.exceptions.ObjectCannotBeCreatedException;

import java.util.Optional;

public interface GenericCRUDService <T, ID> {
    Iterable<T> getAll();
    Optional<T> getById(ID id);
    T create(T entity) throws ObjectCannotBeCreatedException;
}
