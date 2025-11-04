package com.reliaquest.api.webclient;

import com.reliaquest.api.request.CreateEmployeeRequest;
import com.reliaquest.api.response.CreateEmployeeResponse;
import com.reliaquest.api.response.DeleteEmployeeResponse;
import com.reliaquest.api.response.GetAllEmployeesResponse;
import com.reliaquest.api.response.GetEmployeeResponse;

public interface IEmployeeWebClient {

    GetAllEmployeesResponse getAllEmployees();

    GetEmployeeResponse getEmployeeById(String id);

    CreateEmployeeResponse createEmployee(CreateEmployeeRequest createEmployeeRequest);

    DeleteEmployeeResponse deleteEmployee(String id);
}
