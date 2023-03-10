package com.capgemini.training.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "PERSON")
public class Person {

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
    @JsonIdentityReference(alwaysAsId = true)
    private Person counselor;

    @OneToMany
    @JsonManagedReference
    private Set<Person> counselees;

    public Person() {
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

    public Person getCounselor() {
        return counselor;
    }

    public void setCounselor(Person counselor) {
        this.counselor = counselor;
    }

    public Set<Person> getCounselees() {
        return counselees;
    }

    public void setCounselees(Set<Person> counselees) {
        this.counselees = counselees;
    }
}
