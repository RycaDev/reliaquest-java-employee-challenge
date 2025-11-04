package com.reliaquest.api.service;

import com.reliaquest.api.model.Employee;
import com.reliaquest.api.response.GetAllEmployeesResponse;
import com.reliaquest.api.webclient.EmployeeWebClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class EmployeeService implements IEmployeeService<Employee> {

    private final EmployeeWebClient employeeWebClient;

    @Autowired
    public EmployeeService(EmployeeWebClient employeeWebClient) {
        this.employeeWebClient = employeeWebClient;
    }

    @Override
    public ResponseEntity<List<Employee>> getAllEmployees() {

        try {
            GetAllEmployeesResponse getAllEmployeesResponse = employeeWebClient.getAllEmployees();
            return ResponseEntity.ok(getAllEmployeesResponse.getData());
        } catch (Exception e) {
           log.error(e.getMessage());
           return ResponseEntity.notFound().build();
        }

    }
}
