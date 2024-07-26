package com.example.demo.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import com.example.demo.entitys.Department;
import com.example.demo.models.DepartmentRequest;
import com.example.demo.services.DepartmentService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
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

@WebMvcTest(DepartmentController.class)
class DepartmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DepartmentService departmentService;

    private ObjectMapper mapper = new ObjectMapper();

    private Department department1;
    private Department department2;
    private DepartmentRequest departmentRequest;
    String json;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        department1 = new Department(1L, "HR", "AAA");
        department2 = new Department(2L, "IT", "BBB");
        departmentRequest = new DepartmentRequest("Finance", "CCC");
        json = mapper.writeValueAsString(departmentRequest);
    }


    @Test
    void getDepartments() throws Exception {
        List<Department> allDepartments = Arrays.asList(department1, department2);

        given(departmentService.getAllDepartments()).willReturn(allDepartments);

        mockMvc.perform(get("/api/departments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(department1.getName())))
                .andExpect(jsonPath("$[1].name", is(department2.getName())));
    }

    @Test
    void getDepartment() throws Exception {
        given(departmentService.getDepartmentById(department1.getId())).willReturn(department1);

        mockMvc.perform(get("/api/departments/{id}", department1.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(department1.getName())));
    }

    @Test
    void addDepartment() throws Exception {
        given(departmentService.addDepartment(any(DepartmentRequest.class))).willReturn(department1);

        mockMvc.perform(post("/api/departments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(department1.getName())));
    }

    @Test
    void updateDepartment() throws Exception {
        given(departmentService.updateDepartment(anyLong(), any(DepartmentRequest.class))).willReturn(department1);

        mockMvc.perform(put("/api/departments/{id}", department1.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(department1.getName())));
    }

    @Test
    void deleteDepartment() throws Exception {
        doNothing().when(departmentService).deleteDepartment(department1.getId());

        mockMvc.perform(delete("/api/departments/{id}", department1.getId()))
                .andExpect(status().isOk());
    }
}