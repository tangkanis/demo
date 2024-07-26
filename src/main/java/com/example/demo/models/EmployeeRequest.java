package com.example.demo.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmployeeRequest {
    @NotBlank
    public String name;

    @NotBlank
    public String position;

    @NotNull
    public double salary;

    @NotNull
    public Long departmentId;
}
