package com.capgemini.training.services;

import com.capgemini.training.exceptions.ObjectCannotBeCreatedException;
import com.capgemini.training.models.Employee;
import com.capgemini.training.models.Level;
import com.capgemini.training.repositories.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {
    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void shouldGetAllPeople() {
        // Arrange
        List<Employee> people = new ArrayList<Employee>();
        people.add(new Employee());
        people.add(new Employee());

        // Act
        when(employeeRepository.findAll()).thenReturn(people);
        Iterable<Employee> result = employeeService.getAll();

        // Assert
        assertEquals(people, result);
    }

    @Test
    void ShouldCreatePersonWithoutCounselor() throws ObjectCannotBeCreatedException {
        // Arrange
        Employee employee = new Employee();
        employee.setFirstname("omer");
        employee.setLastname("ozdemir");

        // Act
        when(employeeRepository.save(employee)).thenReturn(employee);
        Employee created_employee = employeeService.create(employee);
        // Assert
        assertEquals(employee, created_employee);
    }

    @Test
    void getPersonById() {
        // Arrange
        UUID uuid = UUID.randomUUID();
        Employee employee = new Employee();
        employee.setId(uuid);
        employee.setFirstname("Omer");
        employee.setLastname("Ozdemir");
        employee.setLevel(Level.A3);

        // Act
        when(employeeRepository.findById(uuid)).thenReturn(Optional.of(employee));
        Employee employeeDb = employeeService.getById(uuid).get();

        // assert
        assertEquals(employee, employeeDb);
    }

    @Test
    void shouldThrowPersonCannotBeCreatedExceptionWhenCreatingPersonByGivingBadCounselorId() throws ObjectCannotBeCreatedException {
        // Arrange
        Employee employee = new Employee();
        employee.setCounselorId(UUID.randomUUID());

        // Act
        when(employeeService.getById(any(UUID.class))).thenReturn(Optional.empty());
        // Assert
        assertThrows(ObjectCannotBeCreatedException.class, () -> {
            employeeService.create(employee);
        });

    }
    @Test
    void shouldCreatePersonWithValidCounselorId() throws ObjectCannotBeCreatedException {
        // Arrange
        UUID uuid = UUID.randomUUID();
        Employee employee = new Employee();
        employee.setFirstname("omer");
        employee.setLevel(Level.A3);
        employee.setCounselorId(uuid);


        Employee counselor = new Employee();
        counselor.setId(uuid);
        counselor.setLevel(Level.A4);
        // Act
        when(employeeService.getById(any(UUID.class))).thenReturn(Optional.of(counselor));
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
        Employee createdEmployee = employeeService.create(employee);
        // Assert

        assertEquals(employee.getFirstname(), createdEmployee.getFirstname());
        assertEquals(employee.getCounselor().getId(),counselor.getId());


    }
    @Test
    void shouldThrowPersonCannotBeCreatedExceptionWhenCreatingPersonByComparingLevels() throws ObjectCannotBeCreatedException {
        // Arrange
        UUID uuid = UUID.randomUUID();
        Employee employee = new Employee();
        employee.setFirstname("omer");
        employee.setLevel(Level.A4);
        employee.setCounselorId(uuid);

        Employee counselor = new Employee();
        counselor.setId(uuid);
        counselor.setLevel(Level.A3);
        // Act
        when(employeeService.getById(any(UUID.class))).thenReturn(Optional.of(counselor));
        // Assert
        assertThrows(ObjectCannotBeCreatedException.class, () -> {
            employeeService.create(employee);
        });


    }
}