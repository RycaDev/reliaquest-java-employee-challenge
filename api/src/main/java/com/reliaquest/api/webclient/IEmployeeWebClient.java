package com.reliaquest.api.webclient;

import com.reliaquest.api.model.Employee;
import com.reliaquest.api.response.DeleteEmployeeResponse;
import com.reliaquest.api.response.GetAllEmployeesResponse;
import com.reliaquest.api.response.GetEmployeeResponse;
import com.reliaquest.api.response.CreateEmployeeResponse;

public interface IEmployeeWebClient {

    GetAllEmployeesResponse getAllEmployees();

    GetEmployeeResponse getEmployeeById(String id);

    CreateEmployeeResponse createEmployee(Employee employee);

    DeleteEmployeeResponse deleteEmployee(String id);
}
