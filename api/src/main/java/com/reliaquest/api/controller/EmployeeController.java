package com.reliaquest.api.controller;

import com.reliaquest.api.model.Employee;
import com.reliaquest.api.request.CreateEmployeeRequest;
import com.reliaquest.api.service.IEmployeeService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class EmployeeController implements IEmployeeController<Employee, CreateEmployeeRequest> {

    private final IEmployeeService<Employee, CreateEmployeeRequest> employeeService;

    public EmployeeController(IEmployeeService<Employee, CreateEmployeeRequest> employeeService) {
        this.employeeService = employeeService;
        log.debug("EmployeeController created");
    }

    @Override
    public ResponseEntity<List<Employee>> getAllEmployees() {
        log.debug("Received request to get all employees");
        return employeeService.getAllEmployees();
    }

    @Override
    public ResponseEntity<List<Employee>> getEmployeesByNameSearch(String searchString) {
        log.debug("Received request to get all employees by name search");
        return employeeService.getEmployeesByNameSearch(searchString);
    }

    @Override
    public ResponseEntity<Employee> getEmployeeById(String id) {
        log.debug("Received request to get employee by id {}", id);
        return employeeService.getEmployeeById(id);
    }

    @Override
    public ResponseEntity<Integer> getHighestSalaryOfEmployees() {
        log.debug("Received request to get highest salary of employees");
        return employeeService.getHighestSalaryOfEmployees();
    }

    @Override
    public ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames() {
        log.debug("Received request to get all employees by names");
        return employeeService.getTopTenHighestEarningEmployeeNames();
    }

    @Override
    public ResponseEntity<Employee> createEmployee(CreateEmployeeRequest employeeInput) {
        log.debug("Received request to create employee {}", employeeInput);
        return employeeService.createEmployee(employeeInput);
    }

    @Override
    public ResponseEntity<String> deleteEmployeeById(String id) {
        log.debug("Received request to delete employee {}", id);
        return employeeService.deleteEmployeeById(id);
    }
}
