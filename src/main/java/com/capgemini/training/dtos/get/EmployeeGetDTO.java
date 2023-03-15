package com.capgemini.training.dtos.get;
import com.capgemini.training.models.Level;
import com.capgemini.training.models.Unit;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

public class EmployeeGetDTO {

    private UUID id;
    private String firstname;
    private String lastname;

    private Date birthDate;

    private Date startDate;

    private Date endDate;

    private Level level;

    private CounselorDTO counselor;

    private Set<CounseleesDTO> counselees;

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

    public CounselorDTO getCounselor() {
        return counselor;
    }


    public Set<CounseleesDTO> getCounselees() {
        return counselees;
    }

    public Unit getUnit() {
        return unit;
    }

    private Unit unit;

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

    public void setCounselor(CounselorDTO counselor) {
        this.counselor = counselor;
    }

    public void setCounselees(Set<CounseleesDTO> counselees) {
        this.counselees = counselees;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }
}
