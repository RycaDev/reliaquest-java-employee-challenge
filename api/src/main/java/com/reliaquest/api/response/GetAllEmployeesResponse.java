package com.reliaquest.api.response;

import com.reliaquest.api.model.Employee;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetAllEmployeesResponse {
    List<Employee> data;
    String status;
}
