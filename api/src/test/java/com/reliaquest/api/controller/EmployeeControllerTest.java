package com.reliaquest.api.controller;

import com.reliaquest.api.model.Employee;
import com.reliaquest.api.request.CreateEmployeeRequest;
import com.reliaquest.api.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest {

    @Mock
    private EmployeeService employeeService;

    private EmployeeController employeeController;

    private List<Employee> employees = new ArrayList<>(Arrays.asList(
            new Employee(UUID.randomUUID(), "name2", 501, 21, "title2", "email2"),
            new Employee(UUID.randomUUID(), "name3", 502, 22, "title3", "email3"),
            new Employee(UUID.randomUUID(), "name4", 503, 23, "title4", "email4"),
            new Employee(UUID.randomUUID(), "name20", 519, 40, "title20", "email20"),
            new Employee(UUID.randomUUID(), "name5", 504, 24, "title5", "email5"),
            new Employee(UUID.randomUUID(), "name6", 505, 25, "title6", "email6"),
            new Employee(UUID.randomUUID(), "name7", 506, 26, "title7", "email7"),
            new Employee(UUID.randomUUID(), "name8", 507, 27, "title8", "email8"),
            new Employee(UUID.randomUUID(), "name9", 508, 28, "title9", "email9"),
            new Employee(UUID.randomUUID(), "name10", 509, 29, "title10", "email10"),
            new Employee(UUID.randomUUID(), "name19", 518, 39, "title19", "email19"),
            new Employee(UUID.randomUUID(), "name11", 510, 31, "title11", "email11"),
            new Employee(UUID.randomUUID(), "name12", 511, 32, "title12", "email12"),
            new Employee(UUID.randomUUID(), "name17", 516, 37, "title17", "email17"),
            new Employee(UUID.randomUUID(), "name13", 512, 33, "title13", "email13"),
            new Employee(UUID.randomUUID(), "name14", 513, 34, "title14", "email14"),
            new Employee(UUID.randomUUID(), "name15", 514, 35, "title15", "email15"),
            new Employee(UUID.randomUUID(), "name1", 500, 20, "title1", "email1"),
            new Employee(UUID.randomUUID(), "name16", 515, 36, "title16", "email16"),
            new Employee(UUID.randomUUID(), "name18", 517, 38, "title18", "email18")
    ));

    @BeforeEach
    void setUp() {
        employeeController = new EmployeeController(employeeService);
    }

    @Test
    void getAllEmployees_ShouldReturnAllEmployees_Success() {

        when(employeeService.getAllEmployees()).thenReturn(ResponseEntity.ok(employees));

        ResponseEntity<List<Employee>> response = employeeController.getAllEmployees();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(employees, response.getBody());
    }

    @Test
    void getEmployeesByNameSearch_ShouldReturnEmployeesByName_Success() {

        when(employeeService.getEmployeesByNameSearch("name7")).thenReturn(ResponseEntity.ok(List.of(employees.get(6))));

        ResponseEntity<List<Employee>> response = employeeController.getEmployeesByNameSearch("name7");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(employees.get(6), response.getBody().get(0));
    }

    @Test
    void getEmployeeById_ShouldReturnEmployeeById_Success() {

        when(employeeService.getEmployeeById(employees.get(0).getId().toString())).thenReturn(ResponseEntity.ok(employees.get(0)));

        ResponseEntity<Employee> response = employeeController.getEmployeeById(employees.get(0).getId().toString());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(employees.get(0), response.getBody());
    }

    @Test
    void getHighestSalaryOfEmployees_ShouldReturnHighestSalaryOfEmployees_Success() {

        when(employeeService.getHighestSalaryOfEmployees()).thenReturn(ResponseEntity.ok(5000));

        ResponseEntity<Integer> response = employeeController.getHighestSalaryOfEmployees();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(5000, response.getBody().intValue());
    }

    @Test
    void getTopTenHighestEarningEmployeeNames_ShouldReturnTopTenHighestEarningEmployeeNames_Success() {

        when(employeeService.getTopTenHighestEarningEmployeeNames()).thenReturn(
                ResponseEntity.ok(employees.stream()
                        .sorted(Comparator.comparing(Employee::getSalary).reversed())
                        .limit(10)
                        .map(Employee::getName)
                        .toList()));

        ResponseEntity<List<String>> response = employeeController.getTopTenHighestEarningEmployeeNames();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<String> employeeNames = response.getBody();

        assertNotNull(employeeNames);
        assertEquals(10, employeeNames.size());
        assertTrue(employeeNames.contains("name20"));
        assertTrue(employeeNames.contains("name19"));
        assertTrue(employeeNames.contains("name18"));
        assertTrue(employeeNames.contains("name17"));
        assertTrue(employeeNames.contains("name16"));
        assertTrue(employeeNames.contains("name15"));
        assertTrue(employeeNames.contains("name14"));
        assertTrue(employeeNames.contains("name13"));
        assertTrue(employeeNames.contains("name12"));
        assertTrue(employeeNames.contains("name11"));
        assertFalse(employeeNames.contains("name10"));
        assertFalse(employeeNames.contains("name9"));
        assertFalse(employeeNames.contains("name8"));
        assertFalse(employeeNames.contains("name7"));
        assertFalse(employeeNames.contains("name6"));
        assertFalse(employeeNames.contains("name5"));
        assertFalse(employeeNames.contains("name4"));
        assertFalse(employeeNames.contains("name3"));
        assertFalse(employeeNames.contains("name2"));
        assertFalse(employeeNames.contains("name1"));
    }

    @Test
    void createEmployee_ShouldCreateEmployee_Success() {

        CreateEmployeeRequest createEmployeeRequest = new CreateEmployeeRequest("name22", 522, 43, "title22", "email22");
        when(employeeService.createEmployee(createEmployeeRequest)).thenReturn(ResponseEntity.ok(new Employee(UUID.randomUUID(), "name22", 522, 43, "title22", "email22")));

        ResponseEntity<Employee> response = employeeController.createEmployee(createEmployeeRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(522, response.getBody().getSalary());
    }

}