package com.example.demo.services;

import com.example.demo.entitys.Department;
import com.example.demo.entitys.Employee;
import com.example.demo.models.EmployeeRequest;
import com.example.demo.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentService departmentService;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAllByDeletedIsFalse();
    }

    public Employee getEmployeeById(Long id) throws Exception {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isPresent()) {
            return employee.get();
        }
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Employee with id: " + id + " not found"
        );
    }

    public Employee addEmployee(EmployeeRequest employeeRequest) throws Exception {
        String employeeName = employeeRequest.getName();
        Long employeeDepartmentId = employeeRequest.getDepartmentId();
        Optional<Employee> employeeOptional = employeeRepository.findByNameAndDepartmentIdAndDeletedIsFalse(employeeName, employeeDepartmentId);
        if (employeeOptional.isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Duplicate Employee Name: " + employeeRequest.getName() + " and DepartmentId: " + employeeRequest.getDepartmentId()
            );
        }
        Employee employee = modelToEntity(employeeRequest);
        return employeeRepository.save(employee);
    }

    public void deleteEmployee(Long id) throws Exception {
        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        if (employeeOptional.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Employee with id: " + id + " not found"
            );
        }
        Employee employee = employeeOptional.get();
        employee.setDeleted(true);
        employeeRepository.save(employee);
    }

    public Employee updateEmployee(Long id, EmployeeRequest employeeRequest) throws Exception {
        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        if (employeeOptional.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Employee with id: " + id + " not found"
            );
        }
        Employee oldEmployee = employeeOptional.get();
        Employee employee = modelToEntity(employeeRequest);
        employee.setId(oldEmployee.getId());
        return employeeRepository.save(employee);
    }

    public List<Employee> getEmployeesByDepartmentId(Long departmentId) {
        return employeeRepository.findAllByDepartmentIdAndDeletedIsFalse(departmentId);
    }

    public Employee modelToEntity(EmployeeRequest employeeRequest) {
        Employee employee = new Employee();
        employee.setName(employeeRequest.getName());
        employee.setPosition(employeeRequest.getPosition());
        employee.setSalary(employeeRequest.getSalary());
        Department department = departmentService.getDepartmentById(employeeRequest.getDepartmentId());
        employee.setDepartment(department);
        return employee;
    }

    public boolean IsDuplicateNameAndDepartmentId(EmployeeRequest employeeRequest, Employee employee) {
        return employee.getName().equals(employeeRequest.getName()) && employee.getDepartment().getId().equals(employeeRequest.getDepartmentId())
                && !employee.isDeleted();
    }

    public Employee softDeleteEmployee(Employee employee) {
        employee.setDeleted(true);
        return employee;
    }
}
