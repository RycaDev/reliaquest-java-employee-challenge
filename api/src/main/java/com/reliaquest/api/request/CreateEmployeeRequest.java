package com.reliaquest.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateEmployeeRequest {

    @JsonProperty("employee_name")
    private String name;

    @JsonProperty("employee_salary")
    private Integer salary;

    @JsonProperty("employee_age")
    private Integer age;

    @JsonProperty("employee_title")
    private String title;

    @JsonProperty("employee_email")
    private String email;
}
