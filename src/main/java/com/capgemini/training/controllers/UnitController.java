package com.capgemini.training.controllers;

import com.capgemini.training.dtos.UnitDTO;
import com.capgemini.training.mappers.UnitMapper;
import com.capgemini.training.models.Unit;
import com.capgemini.training.services.UnitService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "units")
@Api(tags = "Unit controller")
public class UnitController {
    @Autowired
    public UnitService unitService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get all units from database")
    public ResponseEntity<?> getAll() {
        try {
            Iterable<Unit> people = unitService.getAll();
            return ResponseEntity.ok(people);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("an error occurred");
        }
    }

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get unit by UUID from database")
    public ResponseEntity<?> getById(@PathVariable(value = "id") UUID id) {
        try {
            Optional<Unit> person = unitService.getById(id);
            if (person.isPresent()) {
                return ResponseEntity.ok(person.get());
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Person not found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("an error occurred");
        }
    }
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Create an unit")
    public ResponseEntity<?> create(@Valid @RequestBody UnitDTO unit, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }
        try {
            Unit createdUnit = unitService.create(UnitMapper.INSTANCE.unitDtoToUnit(unit));

            UnitDTO responseUnit = UnitMapper.INSTANCE.unitToUnitDto(createdUnit);
            return ResponseEntity.ok(responseUnit);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("an error occurred");
        }
    }
}
