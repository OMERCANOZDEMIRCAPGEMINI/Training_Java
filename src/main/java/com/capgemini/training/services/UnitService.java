package com.capgemini.training.services;

import com.capgemini.training.models.Unit;
import com.capgemini.training.repositories.UnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UnitService implements GenericCRUDService<Unit, UUID> {
    @Autowired
    public UnitRepository unitRepository;

    @Override
    public Iterable<Unit> getAll() {
        return unitRepository.findAll();
    }

    @Override
    public Optional<Unit> getById(UUID uuid) {
        return unitRepository.findById(uuid);
    }

    @Override
    public Unit create(Unit entity) {
        return unitRepository.save(entity);
    }
}
