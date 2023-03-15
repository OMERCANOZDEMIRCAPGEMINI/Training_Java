package com.capgemini.training.dtos.post;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class UnitPostDTO {
    @NotNull(message = "Name cannot be null")
    private String name;
    @JsonProperty(value = "manager_id")
    private UUID managerId;

    public UnitPostDTO() {
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
