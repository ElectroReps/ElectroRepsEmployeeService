package com.electroreps.ElectroRepsEmployeeService.services;

import com.electroreps.ElectroRepsEmployeeService.dtos.EmployeeNameDTO;
import com.electroreps.ElectroRepsEmployeeService.models.Employee;
import com.electroreps.ElectroRepsEmployeeService.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static jakarta.ws.rs.core.Response.noContent;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public ResponseEntity<?> getEmployeeById(Long id) {
        Optional<Employee> employee = employeeRepository.findById(id);

        if (employee.isPresent()) {
            return ResponseEntity.ok(employee.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No employee found");
        }
    }

    public ResponseEntity<?> getEmployeeWithParams(String name) {

        if(name == null || name.isEmpty()) {
            return ResponseEntity.ok(employeeRepository.findAll());
        }

        Optional<Employee> employee = employeeRepository.findByName(name);

        if (employee.isPresent()) {
            return ResponseEntity.ok(employee.get());
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    public ResponseEntity<Employee> postEmployee(Employee employee) {
        Employee savedEmployee = employeeRepository.save(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEmployee);
    }

    public ResponseEntity<?> UpdateEmployee(Long id, Employee employee) {
        Optional<Employee> existingEmployee = employeeRepository.findById(id);
        if ( existingEmployee.isEmpty() ) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No employee found with id " + id);
        }
        return ResponseEntity.ok(existingEmployee.get());
    }

    public ResponseEntity<?> UpdateEmployeeName(Long id, EmployeeNameDTO employee) {

        Optional<Employee> existingEmployee = employeeRepository.findById(id);
        if ( existingEmployee.isEmpty() ) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No employee found with id " + id);
        }else if (employee.getName() == null || employee.getName().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Employee name cannot be empty");
        }
        return ResponseEntity.ok(existingEmployee.get());
    }

    public ResponseEntity<?> deleteEmployee(Long id) {
        Optional<Employee> existingEmployee = employeeRepository.findById(id);
        if (existingEmployee.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No employee found with id " + id);
        }
        return ResponseEntity.ok(existingEmployee.get());
    }


}
