package com.capgemini.training.models;


import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "EMPLOYEE")
@Data
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

}
