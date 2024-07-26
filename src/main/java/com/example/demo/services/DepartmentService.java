package com.example.demo.services;

import com.example.demo.entitys.Department;
import com.example.demo.models.DepartmentRequest;
import com.example.demo.repositories.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public Department getDepartmentById(Long id) {
        Optional<Department> department = departmentRepository.findById(id);
        if (department.isPresent()) {
            return department.get();
        }
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Department with id: " + id + " not found"
        );
    }

    public Department addDepartment(DepartmentRequest departmentRequest) throws Exception {
        Department department = modelToEntity(departmentRequest);
        return departmentRepository.save(department);
    }

    public void deleteDepartment(Long id) {
        Optional<Department> department = departmentRepository.findById(id);
        if (department.isPresent()) {
            departmentRepository.deleteById(id);
            return;
        }
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Department with id: " + id + " not found"
        );
    }

    public Department updateDepartment(Long id, DepartmentRequest departmentRequest) throws Exception {
        Department oldDepartment = departmentRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Department with id: " + id + " not found"
                )
        );
        Department department = modelToEntity(departmentRequest);
        department.setId(oldDepartment.getId());
        return departmentRepository.save(department);
    }

    public Department modelToEntity(DepartmentRequest departmentRequest) {
        Department department = new Department();
        department.setName(departmentRequest.getName());
        department.setLocation(departmentRequest.getLocation());
        return department;
    }
}
