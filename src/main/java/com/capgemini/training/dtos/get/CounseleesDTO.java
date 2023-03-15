package com.capgemini.training.dtos.get;

import com.capgemini.training.models.Level;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.UUID;

public class CounseleesDTO {
    private UUID id;
    private String firstname;
    private String lastname;
    @JsonFormat(pattern = "dd/mm/yyyy")

    private Date birthDate;
    @JsonFormat(pattern = "dd/mm/yyyy")

    private Date startDate;
    @JsonFormat(pattern = "dd/mm/yyyy")

    private Date endDate;
    private Level level;
    private String unit;

    public UUID getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Level getLevel() {
        return level;
    }

    public String getUnit() {
        return unit;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
