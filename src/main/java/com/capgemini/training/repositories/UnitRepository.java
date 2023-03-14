package com.capgemini.training.repositories;

import com.capgemini.training.models.Unit;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface UnitRepository extends CrudRepository<Unit, UUID> {
}
