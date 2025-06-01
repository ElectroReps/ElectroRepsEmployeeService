package com.electroreps.ElectroRepsEmployeeService.services;

import com.electroreps.ElectroRepsEmployeeService.dtos.EmployeeNameDTO;
import com.electroreps.ElectroRepsEmployeeService.models.Employee;
import com.electroreps.ElectroRepsEmployeeService.repositories.EmployeeRepository;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class EmployeeServiceTests {


    @MockitoBean
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeService employeeService;

    @Test
    public void test_get_employee_by_id() {
        Employee exampleEmployee = new Employee();
        exampleEmployee.setName("John Doe");

        Mockito.when(employeeRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(exampleEmployee));
        var response = employeeService.getEmployeeById(1L);
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertEquals("John Doe", ((Employee) response.getBody()).getName());

    }

    @Test
    public void test_get_employee_by_id_not_found() {
        Mockito.when(employeeRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.empty());
        var response = employeeService.getEmployeeById(1L);
        assertEquals(HttpStatusCode.valueOf(404), response.getStatusCode());
        assertEquals("No employee found with id 1", response.getBody());
    }

    @Test
    public void test_get_employee_with_params_no_params() {
        Mockito.when(employeeRepository.findAll()).thenReturn(
                List.of(new Employee(1L, "John Doe"), new Employee(2L, "Jane Doe"))
        );

        var response = employeeService.getEmployeeWithParams(null);
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        List<Employee> employees = (List<Employee>) response.getBody();
        assertNotNull(employees);
        assertEquals(2, employees.size());
        assertEquals("John Doe", employees.get(0).getName());
        assertEquals("Jane Doe", employees.get(1).getName());

    }

    @Test
    public void test_get_employee_with_params_name() {

        Mockito.when(employeeRepository.findByName(Mockito.anyString())).thenReturn(
                List.of(new Employee(1L, "John Doe"))
        );

        var response = employeeService.getEmployeeWithParams("John Doe");
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        List<Employee> employees = (List<Employee>) response.getBody();
        assertNotNull(employees);
        assertEquals(1, employees.size());
        assertEquals("John Doe", employees.get(0).getName());
    }

    @Test
    public void test_post_employee() {
        Employee exampleEmployee = new Employee();
        exampleEmployee.setName("John Doe");

        Mockito.when(employeeRepository.save(Mockito.any(Employee.class)))
                .thenReturn(exampleEmployee);

        var response = employeeService.postEmployee(exampleEmployee);
        assertEquals(HttpStatusCode.valueOf(201), response.getStatusCode());
        assertEquals("John Doe", ((Employee) response.getBody()).getName());
    }

    @Test
    public void test_update_employee() {
        Employee exampleEmployee = new Employee();
        exampleEmployee.setId(1L);
        exampleEmployee.setName("John Doe");

        Mockito.when(employeeRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(exampleEmployee));

        var response = employeeService.UpdateEmployee(1L, exampleEmployee);

        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertEquals("John Doe", ((Employee) response.getBody()).getName());
    }

    @Test
    public void test_update_employee_not_found() {
        Employee exampleEmployee = new Employee();
        exampleEmployee.setId(1L);
        exampleEmployee.setName("John Doe");

        Mockito.when(employeeRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.empty());

        var response = employeeService.UpdateEmployee(1L, exampleEmployee);
        assertEquals(HttpStatusCode.valueOf(404), response.getStatusCode());
        assertEquals("No employee found with id 1", response.getBody());

    }

    @Test
    public void test_update_employee_name() {
        Employee exampleEmployee = new Employee();
        exampleEmployee.setId(1L);
        exampleEmployee.setName("John Doe");

        EmployeeNameDTO exampleEmployeeDTO = new EmployeeNameDTO();
        exampleEmployee.setName("Jane Doe");

        Mockito.when(employeeRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(exampleEmployee));

        Mockito.when(employeeRepository.save(Mockito.any(Employee.class)))
                .thenReturn(exampleEmployee);

        var response = employeeService.UpdateEmployeeName(1L, exampleEmployeeDTO);
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertEquals("Jane Doe", ((Employee) response.getBody()).getName());



    }

    @Test
    public void test_update_employee_name_not_found() {
        Mockito.when(employeeRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.empty());

        EmployeeNameDTO exampleEmployeeDTO = new EmployeeNameDTO();
        exampleEmployeeDTO.setName("Jane Doe");
        var response = employeeService.UpdateEmployeeName(1L, exampleEmployeeDTO);
        assertEquals(HttpStatusCode.valueOf(404), response.getStatusCode());
        assertEquals("No employee found with id 1", response.getBody());

    }

    @Test
    public void test_delete_employee() {
        Employee exampleEmployee = new Employee();
        exampleEmployee.setId(1L);
        exampleEmployee.setName("John Doe");

        Mockito.when(employeeRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(exampleEmployee));

        Mockito.doNothing().when(employeeRepository).delete(Mockito.any(Employee.class));

        var response = employeeService.deleteEmployee(1L);
        assertEquals(HttpStatusCode.valueOf(204), response.getStatusCode());


    }

    @Test
    public void test_delete_employee_not_found() {

        Mockito.when(employeeRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.empty());
        var response = employeeService.deleteEmployee(1L);
        assertEquals(HttpStatusCode.valueOf(404), response.getStatusCode());
        assertEquals("No employee found with id 1", response.getBody());
    }
}
