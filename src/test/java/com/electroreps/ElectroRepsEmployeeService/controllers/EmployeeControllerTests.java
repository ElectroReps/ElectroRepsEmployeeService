package com.electroreps.ElectroRepsEmployeeService.controllers;

import com.electroreps.ElectroRepsEmployeeService.dtos.EmployeeNameDTO;
import com.electroreps.ElectroRepsEmployeeService.models.Employee;
import com.electroreps.ElectroRepsEmployeeService.repositories.EmployeeRepository;
import com.electroreps.ElectroRepsEmployeeService.services.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class EmployeeControllerTests {


    @MockitoBean
    private EmployeeService employeeService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testGET_Id() throws Exception{
       final ResponseEntity response = new ResponseEntity(HttpStatus.OK);
       Mockito.when(employeeService.getEmployeeById(anyLong()))
                .thenReturn(response);

        MvcResult mvcResult = mockMvc.perform(get("/employees/9000")).andExpect(status().isOk()).andReturn();
    }

    @Test
    public void testGET_Id_NotFound() throws Exception {
        Mockito.when(employeeService.getEmployeeById(anyLong()))
                .thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));

        MvcResult mvcResult = mockMvc.perform(get("/employees/9000"))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void testGET_EmployeeWithParams() throws Exception {
        Mockito.when(employeeService.getEmployeeWithParams(Mockito.anyString()))
                .thenReturn(new ResponseEntity<>(HttpStatus.OK));

        MvcResult mvcResult = mockMvc.perform(get("/employees")
                .param("name", "John Doe"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void testPOST_Employee() throws Exception {
        Employee employee = new Employee();
        employee.setName("John Doe");

        Mockito.when(employeeService.postEmployee(Mockito.any(Employee.class)))
                .thenReturn(new ResponseEntity<>(employee, HttpStatus.CREATED));

        MvcResult mvcResult = mockMvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isCreated())
                .andReturn();
    }

    @Test
    public void testPOST_Employee_Invalid() throws Exception {
        Employee employee = new Employee();
        // Invalid name

        Mockito.when(employeeService.postEmployee(Mockito.any(Employee.class)))
                .thenReturn(new ResponseEntity<>(HttpStatus.BAD_REQUEST));

        // i wnt the response to be bad request and the body to contain the error message "Validation failed, please send a valid Employee entity as request body "
        MvcResult mvcResult = mockMvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isBadRequest())
                .andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();
        assertEquals("Validation failed, please send a valid Employee entity as request body ", responseBody); // Ensure the error message is correct
    }

    @Test
    public void testPUT_Employee() throws Exception {
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setName("John Doe");

        Mockito.when(employeeService.UpdateEmployee(Mockito.anyLong(), Mockito.any(Employee.class)))
                .thenReturn(new ResponseEntity<>( HttpStatus.OK));

        MvcResult mvcResult = mockMvc.perform(put("/employees/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void testPUT_Employee_NotFound() throws Exception {
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setName("John Doe");

        Mockito.when(employeeService.UpdateEmployee(Mockito.anyLong(), Mockito.any(Employee.class)))
                .thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));

        MvcResult mvcResult = mockMvc.perform(put("/employees/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void testPUT_EmployeeInvalid() throws Exception {
        Employee employee = new Employee();
        // Invalid name

        Mockito.when(employeeService.UpdateEmployee(Mockito.anyLong(), Mockito.any(Employee.class)))
                .thenReturn(new ResponseEntity<>(HttpStatus.BAD_REQUEST));

        MvcResult mvcResult = mockMvc.perform(put("/employees/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isBadRequest())
                .andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();
        assertEquals("Validation failed, please send a valid Employee entity as request body ", responseBody); // Ensure the error message is correct
    }

    @Test
    public void testPATCH_EmployeeName() throws Exception {

        EmployeeNameDTO employeeName = new EmployeeNameDTO();

        Mockito.when(employeeService.UpdateEmployeeName(anyLong(), Mockito.any(EmployeeNameDTO.class)))
                .thenReturn(new ResponseEntity<>(HttpStatus.OK));

        MvcResult mvcResult = mockMvc.perform(patch("/employees/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employeeName)))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void testDELETE_Employee() throws Exception {
        Mockito.when(employeeService.deleteEmployee(Mockito.anyLong()))
                .thenReturn(new ResponseEntity<>(HttpStatus.NO_CONTENT));

        MvcResult mvcResult = mockMvc.perform(delete("/employees/1"))
                .andExpect(status().isNoContent())
                .andReturn();
    }

    @Test
    public void testDELETE_Employee_NotFound() throws Exception {
        Mockito.when(employeeService.deleteEmployee(Mockito.anyLong()))
                .thenReturn(new ResponseEntity<>( HttpStatus.NOT_FOUND));

        MvcResult mvcResult = mockMvc.perform(delete("/employees/1"))
                .andExpect(status().isNotFound())
                .andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();
    }





}
