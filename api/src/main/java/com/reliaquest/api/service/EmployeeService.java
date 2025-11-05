package com.reliaquest.api.service;

import com.reliaquest.api.model.Employee;
import com.reliaquest.api.request.CreateEmployeeRequest;
import com.reliaquest.api.response.CreateEmployeeResponse;
import com.reliaquest.api.response.DeleteEmployeeResponse;
import com.reliaquest.api.response.GetAllEmployeesResponse;
import com.reliaquest.api.response.GetEmployeeResponse;
import com.reliaquest.api.webclient.EmployeeWebClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
public class EmployeeService implements IEmployeeService<Employee, CreateEmployeeRequest> {

    private final EmployeeWebClient employeeWebClient;

    @Autowired
    public EmployeeService(EmployeeWebClient employeeWebClient) {
        this.employeeWebClient = employeeWebClient;
    }

    @Override
    public ResponseEntity<List<Employee>> getAllEmployees() {

        try {
            GetAllEmployeesResponse allEmployeesResponse = employeeWebClient.getAllEmployees();
            return ResponseEntity.ok(allEmployeesResponse.getData());
        } catch (Exception e) {
           log.error(e.getMessage());
           return ResponseEntity.internalServerError().build();
        }
    }

    @Override
    public ResponseEntity<List<Employee>> getEmployeesByNameSearch(String searchString) {

        try {
            List<Employee> employees = employeeWebClient.getAllEmployees().getData();
            List<Employee> employeeByName = employees.stream()
                    .filter(employee -> employee.getName().toLowerCase().contains(searchString.toLowerCase()))
                    .toList();

            return ResponseEntity.ok(employeeByName);

        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @Override
    public ResponseEntity<Employee> getEmployeeById(String id) {

        try {
            GetEmployeeResponse employeeResponse = employeeWebClient.getEmployeeById(id);
            return ResponseEntity.ok(employeeResponse.getData());
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @Override
    public ResponseEntity<Integer> getHighestSalaryOfEmployees() {

        try {
            List<Employee> employees = employeeWebClient.getAllEmployees().getData();
            int highestSalary = employees.stream()
                    .mapToInt(Employee::getSalary)
                    .max()
                    .orElse(0);

            return ResponseEntity.ok(highestSalary);

        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @Override
    public ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames() {

        try {
            List<Employee> employees = employeeWebClient.getAllEmployees().getData();
            List<String> top10Earners = employees.stream()
                    .sorted(Comparator.comparing(Employee::getSalary).reversed())
                    .limit(10)
                    .map(Employee::getName)
                    .toList();

            return ResponseEntity.ok(top10Earners);

        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @Override
    public ResponseEntity<Employee> createEmployee(CreateEmployeeRequest employeeInput) {

        if (!validateInput(employeeInput)) {
            return ResponseEntity.badRequest().build();
        }

        try {
            CreateEmployeeResponse createEmployeesResponse = employeeWebClient.createEmployee(employeeInput);
            return ResponseEntity.ok(createEmployeesResponse.getData());
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @Override
    public ResponseEntity<String> deleteEmployeeById(String id) {

        try {
            GetEmployeeResponse employeeResponse = employeeWebClient.getEmployeeById(id);
            DeleteEmployeeResponse allEmployeesResponse = employeeWebClient.deleteEmployee(employeeResponse.getData().getName());

            if (allEmployeesResponse.getData() == null || !allEmployeesResponse.getData()) {
                log.error("Delete employee failed");
                throw new RuntimeException("Delete employee failed");
            }

            return ResponseEntity.ok(allEmployeesResponse.getStatus());
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    private boolean validateInput(CreateEmployeeRequest employeeInput) {

        if ((employeeInput.getName() == null || employeeInput.getName().isEmpty()) ||
                (employeeInput.getSalary() < 0) ||
                (employeeInput.getAge() < 16 || employeeInput.getAge() > 75) ||
                (employeeInput.getTitle() == null || employeeInput.getTitle().isEmpty())
        ) {
            return false;
        } else {
            return true;
        }
    }
}
