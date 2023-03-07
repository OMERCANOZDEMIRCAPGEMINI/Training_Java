package com.capgemini.training.models;


import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Table(name = "UNITS")
public class Unit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;
    @NotNull
    private String name;
    @OneToOne
    @JoinColumn(name = "manager_id", referencedColumnName = "id")
    private Person manager;

    public Unit() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Person getManager() {
        return manager;
    }

    public void setManager(Person manager) {
        this.manager = manager;
    }
}
