package com.example.demo.controllers;

import com.example.demo.entitys.Department;
import com.example.demo.models.DepartmentRequest;
import com.example.demo.services.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;


    @GetMapping
    public List<Department> getDepartments() {
        return departmentService.getAllDepartments();
    }

    @GetMapping("/{id}")
    public Department getDepartment(@PathVariable Long id) {
        return departmentService.getDepartmentById(id);
    }

    @PostMapping
    public Department addDepartment(@Valid @RequestBody DepartmentRequest departmentRequest) throws Exception {
        return departmentService.addDepartment(departmentRequest);
    }

    @PutMapping("/{id}")
    public Department updateDepartment(@PathVariable Long id, @Valid @RequestBody DepartmentRequest departmentRequest) throws Exception {
        return departmentService.updateDepartment(id, departmentRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
    }
}
