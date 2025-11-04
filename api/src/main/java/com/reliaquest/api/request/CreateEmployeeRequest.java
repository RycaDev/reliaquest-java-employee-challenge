package com.reliaquest.api.request;

import com.reliaquest.api.model.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateEmployeeRequest {

    private String name;
    private Integer salary;
    private Integer age;
    private String title;
    private String email;

    public Employee toEmployee() {
        return new Employee(null, name, salary, age, title, email);
    }
}
