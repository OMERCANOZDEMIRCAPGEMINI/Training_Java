package com.capgemini.training.mappers;


import com.capgemini.training.dtos.EmployeeDTO;
import com.capgemini.training.models.Employee;
import com.capgemini.training.models.Level;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Date;

public class EmployeeMapperTest {
    @Test
    void shouldMapPerson(){
        // Arrange
        Employee employee = new Employee();
        employee.setFirstname("Omer");
        employee.setLastname("Ozdemir");
        employee.setBirthDate(new Date("05/07/2000"));
        employee.setStartDate(new Date("06/02/2023"));
        employee.setLevel(Level.A3);

        // Act
        EmployeeDTO mappedPerson = EmployeeMapper.INSTANCE.employeeToEmployeeDto(employee);

        //Assert
        assertNotNull(mappedPerson);
        assertEquals(employee.getFirstname(),mappedPerson.getFirstname());
        assertEquals(employee.getLastname(),mappedPerson.getLastname());
        assertEquals(employee.getLevel(),mappedPerson.getLevel());
        assertEquals(employee.getBirthDate(),mappedPerson.getBirthDate());
        assertEquals(employee.getStartDate(),mappedPerson.getStartDate());
    }
}
