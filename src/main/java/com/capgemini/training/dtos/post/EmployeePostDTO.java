package com.capgemini.training.dtos.post;

import com.capgemini.training.models.Level;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

public class EmployeePostDTO {

    @NotNull(message = "Firstname must not be empty")
    private String firstname;
    @NotNull(message = "Lastname must not be empty")
    private String lastname;
    @NotNull(message = "Start date must not be empty")
    @JsonFormat(pattern = "dd/mm/yyyy")
    private Date birthDate;
    @NotNull(message = "Start date must not be empty")
    @JsonFormat(pattern = "dd/mm/yyyy")
    private Date startDate;
    @JsonFormat(pattern = "dd/mm/yyyy")
    private Date endDate;
    @NotNull(message = "Level must not be empty")
    private Level level;
    @JsonProperty(value = "counselor_id")
    private UUID counselorId;

    @NotNull(message = "Employee must have an unit")
    @JsonProperty("unit_id")
    private UUID unitId;


    public EmployeePostDTO() {
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public UUID getCounselorId() {
        return counselorId;
    }

    public void setCounselorId(UUID counselorId) {
        this.counselorId = counselorId;
    }

    public UUID getUnitId() {
        return unitId;
    }

    public void setUnitId(UUID unitId) {
        this.unitId = unitId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }
}
