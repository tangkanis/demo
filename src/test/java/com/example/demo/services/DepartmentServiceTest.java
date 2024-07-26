package com.example.demo.services;

import com.example.demo.entitys.Department;
import com.example.demo.models.DepartmentRequest;
import com.example.demo.repositories.DepartmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class DepartmentServiceTest {

    @InjectMocks
    private DepartmentService departmentService;

    @Mock
    private DepartmentRepository departmentRepository;

    private Department department1;
    private Department department2;
    private DepartmentRequest departmentRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        department1 = new Department(1L, "HR", "Building A");
        department2 = new Department(2L, "IT", "Building B");
        departmentRequest = new DepartmentRequest("Finance", "Building C");
    }

    @Test
    void getAllDepartments() {
        List<Department> allDepartments = Arrays.asList(department1, department2);
        when(departmentRepository.findAll()).thenReturn(allDepartments);

        List<Department> result = departmentService.getAllDepartments();

        assertEquals(2, result.size());
        assertEquals(department1.getName(), result.get(0).getName());
        assertEquals(department2.getName(), result.get(1).getName());
    }

    @Test
    void getDepartmentById() {
        when(departmentRepository.findById(department1.getId())).thenReturn(Optional.of(department1));

        Department result = departmentService.getDepartmentById(department1.getId());

        assertNotNull(result);
        assertEquals(department1.getName(), result.getName());
    }

    @Test
    void getDepartmentByIdNotFound() {
        when(departmentRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            departmentService.getDepartmentById(1L);
        });

//        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertTrue(exception.getReason().contains("Department with id: 1 not found"));
    }

    @Test
    void addDepartment() throws Exception {
        when(departmentRepository.save(any(Department.class))).thenReturn(department1);

        Department result = departmentService.addDepartment(departmentRequest);

        assertNotNull(result);
        assertEquals(department1.getName(), result.getName());
    }

    @Test
    void deleteDepartment() {
        when(departmentRepository.findById(department1.getId())).thenReturn(Optional.of(department1));
        doNothing().when(departmentRepository).deleteById(department1.getId());

        departmentService.deleteDepartment(department1.getId());

        verify(departmentRepository, times(1)).deleteById(department1.getId());
    }

    @Test
    void deleteDepartmentNotFound() {
        when(departmentRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            departmentService.deleteDepartment(1L);
        });

//        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertTrue(exception.getReason().contains("Department with id: 1 not found"));
    }

    @Test
    void updateDepartment() throws Exception {
        when(departmentRepository.findById(department1.getId())).thenReturn(Optional.of(department1));
        when(departmentRepository.save(any(Department.class))).thenReturn(department1);

        Department result = departmentService.updateDepartment(department1.getId(), departmentRequest);

        assertNotNull(result);
        assertEquals(department1.getName(), result.getName());
    }

    @Test
    void updateDepartmentNotFound() {
        when(departmentRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            departmentService.updateDepartment(1L, departmentRequest);
        });

//        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertTrue(exception.getReason().contains("Department with id: 1 not found"));
    }
}