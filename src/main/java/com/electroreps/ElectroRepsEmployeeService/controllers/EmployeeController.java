package com.electroreps.ElectroRepsEmployeeService.controllers;

import com.electroreps.ElectroRepsEmployeeService.dtos.EmployeeNameDTO;
import com.electroreps.ElectroRepsEmployeeService.models.Employee;
import com.electroreps.ElectroRepsEmployeeService.services.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/{employeeId}")
    public ResponseEntity<?> getEmployeeById(@PathVariable Long employeeId) {
        return employeeService.getEmployeeById(employeeId);
    }

    @GetMapping
    public ResponseEntity<?> getEmployeeWithParams(
            @RequestParam(required = false) String name) {
        return employeeService.getEmployeeWithParams(name);
    }
    @PostMapping
    public ResponseEntity<?> postEmployee(@RequestBody @Valid Employee employee) {
        return employeeService.postEmployee(employee);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable Long id, @RequestBody @Valid Employee employee) {
        return employeeService.UpdateEmployee(id, employee);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateEmployeeName(@PathVariable Long id, @RequestBody EmployeeNameDTO employee) {
        return employeeService.UpdateEmployeeName(id, employee);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
        return employeeService.deleteEmployee(id);
    }
}
