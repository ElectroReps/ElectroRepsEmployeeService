package com.electroreps.ElectroRepsEmployeeService.repositories;

import com.electroreps.ElectroRepsEmployeeService.models.Employee;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
public class EmployeeRepoTests {

    @Autowired
    private EmployeeRepository employeeRepository;

    Employee exampleEmployee;
    // This class contains tests for the EmployeeRepository
    @BeforeEach
    public void setUp() {

        // This method can be used to set up any common test data or state before each test
        // For example, you could create some initial employees in the repository if needed
        employeeRepository.deleteAll();

        exampleEmployee = new Employee();
        exampleEmployee.setName("John Doe");
        employeeRepository.save(exampleEmployee);
        // }
    }

    @Test
    public void test_find_all() {
        Employee exampleEmployee2 = new Employee();
        exampleEmployee2.setName("John Doe 2");
        employeeRepository.save(exampleEmployee2);

        Employee exampleEmployee3 = new Employee();
        exampleEmployee3.setName("John Doe 3");
        employeeRepository.save(exampleEmployee3);

        List<Employee> employees = employeeRepository.findAll();
        assertNotNull(employees);
        assertNotNull(employees.get(0).getName());
        assertEquals("John Doe", employees.get(0).getName());
        assertNotNull(employees.get(1).getName());
        assertEquals("John Doe 2", employees.get(1).getName());
        assertNotNull(employees.get(2).getName());
        assertEquals("John Doe 3", employees.get(2).getName());


    }

    @Test
    public void test_find_by_name() {
        List<Employee> employees = employeeRepository.findByName("John Doe");
        assertNotNull(employees);
        assertEquals(1, employees.size());
        assertNotNull(employees.get(0).getName());
        assertEquals("John Doe", employees.get(0).getName());
    }


    @Test
    public void test_save_employee() {
        assertNotNull(exampleEmployee);
        assertNotNull(exampleEmployee.getId());
        assertEquals("John Doe", exampleEmployee.getName());

    }

    @Test
    public void test_update_employee() {
        exampleEmployee.setName("Jane Doe");
        Employee updatedEmployee = employeeRepository.save(exampleEmployee);
        assertNotNull(updatedEmployee);
        assertEquals("Jane Doe", updatedEmployee.getName());
    }

    @Test
    public void test_delete_employee() {
        Long id = exampleEmployee.getId();
        assertNotNull(id);
        employeeRepository.deleteById(id);
        assertFalse(employeeRepository.findById(id).isPresent(), "Employee should be deleted");
    }
}
