package com.electroreps.ElectroRepsEmployeeService.controllers;

import com.electroreps.ElectroRepsEmployeeService.repositories.EmployeeRepository;
import com.electroreps.ElectroRepsEmployeeService.services.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
public class EmployeeControllerTests {

    @MockitoBean
    private EmployeeRepository employeeRepository;

    @MockitoBean
    private EmployeeService employeeService;





}
