package com.electroreps.ElectroRepsEmployeeService.repositories;

import com.electroreps.ElectroRepsEmployeeService.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository< Employee, Long> {

    List<Employee> findByName(String name);
}
