package com.capgemini.training.controllers;

import com.capgemini.training.dtos.post.UnitPostDTO;
import com.capgemini.training.exceptions.ValidationException;
import com.capgemini.training.mappers.UnitMapper;
import com.capgemini.training.models.Unit;
import com.capgemini.training.services.UnitService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(UnitController.class);

    @Autowired
    public UnitService unitService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get all units from database")
    public ResponseEntity<?> getAll() throws Exception {
        try {
            Iterable<Unit> units = unitService.getAll();
            return ResponseEntity.ok(units);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new Exception("Something went wrong");
        }
    }

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get unit by UUID from database")
    public ResponseEntity<?> getById(@PathVariable(value = "id") UUID id) throws Exception {
        try {
            Optional<Unit> unit = unitService.getById(id);
            if (unit.isPresent()) {
                return ResponseEntity.ok(unit.get());
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Unit not found");
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new Exception("Something went wrong");
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Create an unit")
    public ResponseEntity<?> create(@Valid @RequestBody UnitPostDTO unit, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            String errorMessage = String.join(",", errors);
            throw new ValidationException(errorMessage);
        }
        try {
            Unit createdUnit = unitService.create(UnitMapper.INSTANCE.unitDtoToUnit(unit));
            UnitPostDTO responseUnit = UnitMapper.INSTANCE.unitToUnitDto(createdUnit);
            return ResponseEntity.ok(responseUnit);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new Exception("Something went wrong");
        }
    }
}
