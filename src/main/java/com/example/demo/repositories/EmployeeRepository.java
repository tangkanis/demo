package com.example.demo.repositories;

import com.example.demo.entitys.Department;
import com.example.demo.entitys.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findAllByDeletedIsFalse();
    Optional<Employee> findByNameAndDepartmentIdAndDeletedIsFalse(String name, Long departmentId);
    List<Employee> findAllByDepartmentIdAndDeletedIsFalse(Long departmentId);
}