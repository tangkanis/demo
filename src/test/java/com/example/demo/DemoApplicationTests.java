package com.example.demo;

import com.example.demo.controllers.DepartmentController;
import com.example.demo.controllers.EmployeeController;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    EmployeeController employeeController;

    @Autowired
    DepartmentController departmentController;

    @Test
    void contextLoads() {
        Assertions.assertThat(employeeController).isNot(null);
        Assertions.assertThat(departmentController).isNot(null);
    }

}
