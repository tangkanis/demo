package com.example.demo.entitys;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Table(name = "employee")
@AllArgsConstructor
@NoArgsConstructor
public class Employee extends AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id", nullable = false)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String position;

    @NotNull
    private double salary;

    @NotNull
    private boolean deleted = false;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank String getName() {
        return name;
    }

    public void setName(@NotBlank String name) {
        this.name = name;
    }

    public @NotBlank String getPosition() {
        return position;
    }

    public void setPosition(@NotBlank String position) {
        this.position = position;
    }

    @NotNull
    public double getSalary() {
        return salary;
    }

    public void setSalary(@NotNull double salary) {
        this.salary = salary;
    }

    @NotNull
    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(@NotNull boolean deleted) {
        this.deleted = deleted;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Double.compare(salary, employee.salary) == 0 && deleted == employee.deleted && Objects.equals(id, employee.id) && Objects.equals(name, employee.name) && Objects.equals(position, employee.position) && Objects.equals(department, employee.department);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, position, salary, deleted, department);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", position='" + position + '\'' +
                ", salary=" + salary +
                ", deleted=" + deleted +
                ", department=" + department +
                '}';
    }
}
