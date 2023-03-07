package com.capgemini.training.models;


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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;
    @NotNull
    private String firstname;
    @NotNull
    private String lastname;
    @NotNull
    private Date startDate;
    private Date endDate;
    @NotNull
    private Level level;

    @ManyToOne
    @JoinColumn(name = "counselor_id")
    private Person counselor;

    @OneToMany(mappedBy = "counselor")
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
