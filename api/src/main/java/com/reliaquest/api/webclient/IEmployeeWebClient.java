package com.reliaquest.api.webclient;

import com.reliaquest.api.model.Employee;
import com.reliaquest.api.response.DeleteEmployeeResponse;
import com.reliaquest.api.response.GetAllEmployeesResponse;
import com.reliaquest.api.response.GetEmployeeResponse;
import com.reliaquest.api.response.UpdateEmployeeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface IEmployeeWebClient {

    GetAllEmployeesResponse getAllEmployees();

    GetEmployeeResponse getEmployee(UUID id);

    UpdateEmployeeResponse updateEmployee(Employee employee);

    DeleteEmployeeResponse deleteEmployee(UUID id);
}
