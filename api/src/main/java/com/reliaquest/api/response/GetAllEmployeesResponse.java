package com.reliaquest.api.response;

import com.reliaquest.api.model.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GetAllEmployeesResponse {
    List<Employee> data;
    String status;
}
