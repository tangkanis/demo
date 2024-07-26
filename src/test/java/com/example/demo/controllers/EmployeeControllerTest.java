package com.example.demo.controllers;

import com.example.demo.entitys.Department;
import com.example.demo.entitys.Employee;
import com.example.demo.models.DepartmentRequest;
import com.example.demo.models.EmployeeRequest;
import com.example.demo.services.EmployeeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @MockBean
    EmployeeService employeeService;

    @Autowired
    MockMvc mockMvc;
    ObjectMapper mapper = new ObjectMapper();

    private Employee employee1;
    private Employee employee2;
    private Department department;
    private DepartmentRequest departmentRequest;
    private String json;
    private String badJson;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        department = new Department();
        department.setId(1L);
        department.setName("Department 1");
        department.setLocation("Department 1");
        employee1 = new Employee(1L, "test1", "position1", 1000.0, false, department);
        employee2 = new Employee(2L, "test2", "position2", 1000.0, false, department);
        EmployeeRequest employeeRequest = new EmployeeRequest();
        employeeRequest.setName("test");
        employeeRequest.setPosition("position");
        employeeRequest.setSalary(1000.0);
        json = mapper.writeValueAsString(employeeRequest);
        EmployeeRequest badEmployeeRequest = new EmployeeRequest();
        employeeRequest.setName("test");
        badJson = mapper.writeValueAsString(badEmployeeRequest);
    }

    @Test
    void getEmployees() throws Exception {

        List<Employee> employees = Arrays.asList(employee1, employee2);

        Mockito.when(employeeService.getAllEmployees()).thenReturn(employees);

        mockMvc.perform(get("/api/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(employee1.getName())))
                .andExpect(jsonPath("$[1].name", is(employee2.getName())));
    }

    @Test
    void getEmployee() throws Exception {

        Mockito.when(employeeService.getEmployeeById(employee1.getId())).thenReturn(employee1);

        mockMvc.perform(get("/api/employees/{id}", employee1.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(employee1.getId().intValue())))
                .andExpect(jsonPath("$.name", is(employee1.getName())));
    }

    @Test
    void addEmployee() throws Exception {
        given(employeeService.addEmployee(any(EmployeeRequest.class))).willReturn(employee1);

        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(employee1.getName())));
    }

    @Test
    void updateEmployee() throws Exception {
        given(employeeService.updateEmployee(anyLong(), any(EmployeeRequest.class))).willReturn(employee1);

        mockMvc.perform(put("/api/employees/{id}", employee1.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(employee1.getName())));
    }

    @Test
    void deleteEmployee() throws Exception {
        doNothing().when(employeeService).deleteEmployee(employee1.getId());

        mockMvc.perform(delete("/api/employees/{id}", employee1.getId()))
                .andExpect(status().isOk());
    }

    @Test
    void getEmployeesByDepartment() throws Exception {
        List<Employee> itEmployees = Arrays.asList(employee1,employee2);

        given(employeeService.getEmployeesByDepartmentId(employee1.getDepartment().getId())).willReturn(itEmployees);

        mockMvc.perform(get("/api/employees/by-department/{departmentId}", employee1.getDepartment().getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(employee1.getName())));
    }
}