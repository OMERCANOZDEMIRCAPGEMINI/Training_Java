package com.capgemini.training.models;


import com.fasterxml.jackson.annotation.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "EMPLOYEE")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;
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

    @ManyToOne
    @JoinColumn(name = "counselor_id")
    private Employee counselor;

    @Transient
    @JsonIgnore
    private UUID counselorId;
    @Transient
    @JsonIgnore
    private UUID unitId;
    @OneToMany
    private Set<Employee> counselees;

    @ManyToOne
    @JoinColumn(name = "unit_id")
    private Unit unit;

    public Employee() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public UUID getUnitId() {
        return unitId;
    }

    public void setUnitId(UUID unitId) {
        this.unitId = unitId;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public UUID getCounselorId() {
        return counselorId;
    }

    public void setCounselorId(UUID counselorId) {
        this.counselorId = counselorId;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
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
    @JsonBackReference

    public Employee getCounselor() {
        return counselor;
    }

    public void setCounselor(Employee counselor) {
        this.counselor = counselor;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }
    @JsonManagedReference

    public Set<Employee> getCounselees() {
        return counselees;
    }

    public void setCounselees(Set<Employee> counselees) {
        this.counselees = counselees;
    }
}
