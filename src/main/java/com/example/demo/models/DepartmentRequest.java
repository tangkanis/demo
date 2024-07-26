package com.example.demo.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentRequest {

    @NotBlank
    @Size(min = 1, max = 50)
    public String name;

    @NotBlank
    @Size(min = 1, max = 255)
    public String location;

}
