package com.reliaquest.api.response;

import com.reliaquest.api.model.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeleteEmployeeResponse {
    Employee data;
}
