package com.capgemini.training.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class UnitDTO {
    @NotNull(message = "Name cannot be null")
    private String name;
    @JsonProperty(value = "manager_id")
    private UUID managerId;

    public UnitDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getManagerId() {
        return managerId;
    }

    public void setManagerId(UUID managerId) {
        this.managerId = managerId;
    }
}
