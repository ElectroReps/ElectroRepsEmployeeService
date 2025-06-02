package com.electroreps.ElectroRepsEmployeeService.services;

import com.electroreps.ElectroRepsEmployeeService.dtos.EmployeeNameDTO;
import com.electroreps.ElectroRepsEmployeeService.models.Employee;
import com.electroreps.ElectroRepsEmployeeService.repositories.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static jakarta.ws.rs.core.Response.noContent;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public ResponseEntity<?> getEmployeeById(Long id) {
        Optional<Employee> employee = employeeRepository.findById(id);

        if (employee.isPresent()) {
            return ResponseEntity.ok(employee.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No employee found with id " + id);
        }
    }

    public ResponseEntity<?> getEmployeeWithParams(String name) {

        if(name == null) {
            return ResponseEntity.ok(employeeRepository.findAll());
        }

        List<Employee> employees = employeeRepository.findByName(name);
            return ResponseEntity.ok(employees);

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
        employee.setId(existingEmployee.get().getId());
        employeeRepository.save(employee);
        return ResponseEntity.ok(employee);
    }

    public ResponseEntity<?> UpdateEmployeeName(Long id, EmployeeNameDTO employee) {

        Optional<Employee> existingEmployee = employeeRepository.findById(id);
        if ( existingEmployee.isEmpty() ) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No employee found with id " + id);
        }

        if (employee.getName() != null && !employee.getName().isEmpty()) {
            existingEmployee.get().setName(employee.getName());
        }

        employeeRepository.save(existingEmployee.get());
        return ResponseEntity.ok(existingEmployee.get());
    }

    public ResponseEntity<?> deleteEmployee(Long id) {
        Optional<Employee> existingEmployee = employeeRepository.findById(id);
        if (existingEmployee.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No employee found with id " + id);
        }
        employeeRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}
