package com.reliaquest.api.service;

import com.reliaquest.api.model.Employee;
import com.reliaquest.api.request.CreateEmployeeRequest;
import com.reliaquest.api.response.CreateEmployeeResponse;
import com.reliaquest.api.response.DeleteEmployeeResponse;
import com.reliaquest.api.response.GetAllEmployeesResponse;
import com.reliaquest.api.response.GetEmployeeResponse;
import com.reliaquest.api.webclient.EmployeeWebClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeWebClient employeeWebClient;

    private EmployeeService employeeService;

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

        employeeService = new EmployeeService(employeeWebClient);
    }

    @Test
    void getAllEmployees_ShouldReturnAllEmployees_Success() {

        when(employeeWebClient.getAllEmployees()).thenReturn(new GetAllEmployeesResponse(employees, "success"));

        ResponseEntity<List<Employee>> employeesResponse = employeeService.getAllEmployees();

        assertEquals(HttpStatus.OK, employeesResponse.getStatusCode());

        List<Employee> employeeData = employeesResponse.getBody();

        assertNotNull(employeeData);
        assertEquals(employeeData.size(), employees.size());
    }

    @Test
    void getAllEmployees_ShouldReturnAllEmployees_InternalServerError() {

        when(employeeWebClient.getAllEmployees()).thenThrow(new RuntimeException("Failed to fetch employees"));

        ResponseEntity<List<Employee>> employeesResponse = employeeService.getAllEmployees();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, employeesResponse.getStatusCode());
        assertNull(employeesResponse.getBody());
    }

    @Test
    void getEmployeesByNameSearch_ShouldReturnRecordWithName18_Success() {

        when(employeeWebClient.getAllEmployees()).thenReturn(new GetAllEmployeesResponse(employees, "success"));

        ResponseEntity<List<Employee>> employeesResponse = employeeService.getEmployeesByNameSearch("me18");

        assertEquals(HttpStatus.OK, employeesResponse.getStatusCode());

        List<Employee> employeeData = employeesResponse.getBody();

        assertNotNull(employeeData);
        assertEquals(1, employeeData.size());
        assertEquals(employees.get(19).getName(), employeeData.get(0).getName());
    }

    @Test
    void getEmployeesByNameSearch_ShouldReturnRecordWithName18_InternalServerError() {

        when(employeeWebClient.getAllEmployees()).thenThrow(new RuntimeException("Failed to fetch employee"));

        ResponseEntity<List<Employee>> employeesResponse = employeeService.getEmployeesByNameSearch("me18");

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, employeesResponse.getStatusCode());
        assertNull(employeesResponse.getBody());
    }

    @Test
    void getEmployeeById_ShouldReturnRecordWithName18_Success() {

        String id = employees.get(15).getId().toString();

        when(employeeWebClient.getEmployeeById(id)).thenReturn(new GetEmployeeResponse(employees.get(15), "success"));

        ResponseEntity<Employee> employeesResponse = employeeService.getEmployeeById(id);

        assertEquals(HttpStatus.OK, employeesResponse.getStatusCode());

        Employee employeeData = employeesResponse.getBody();

        assertNotNull(employeeData);
        assertEquals(employees.get(15).getName(), employeeData.getName());
    }

    @Test
    void getEmployeeById_ShouldReturnRecordWithName18_InternalServerError() {

        String id = employees.get(17).getId().toString();

        when(employeeWebClient.getEmployeeById(id)).thenThrow(new RuntimeException("Failed to fetch employee"));

        ResponseEntity<Employee> employeesResponse = employeeService.getEmployeeById(id);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, employeesResponse.getStatusCode());
        assertNull(employeesResponse.getBody());
    }

    @Test
    void getHighestSalaryOfEmployees_ShouldReturn519AsHighestSalary_Success() {

        when(employeeWebClient.getAllEmployees()).thenReturn(new GetAllEmployeesResponse(employees, "success"));

        ResponseEntity<Integer> employeesResponse = employeeService.getHighestSalaryOfEmployees();

        assertEquals(HttpStatus.OK, employeesResponse.getStatusCode());

        Integer employeeData = employeesResponse.getBody();

        assertNotNull(employeeData);
        assertEquals(519, employeeData);
    }

    @Test
    void getHighestSalaryOfEmployees_ShouldReturn519AsHighestSalary_InternalServerError() {

        when(employeeWebClient.getAllEmployees()).thenThrow(new RuntimeException("Failed to fetch highest salary"));

        ResponseEntity<Integer> employeesResponse = employeeService.getHighestSalaryOfEmployees();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, employeesResponse.getStatusCode());
        assertNull(employeesResponse.getBody());
    }


    @Test
    void getTopTenHighestEarningEmployeeNames_ShouldReturnTopTenHighestEarningEmployeeNames_Success() {

        when(employeeWebClient.getAllEmployees()).thenReturn(new GetAllEmployeesResponse(employees, "success"));

        ResponseEntity<List<String>> employeesResponse = employeeService.getTopTenHighestEarningEmployeeNames();

        assertEquals(HttpStatus.OK, employeesResponse.getStatusCode());

        List<String> employeeData = employeesResponse.getBody();

        assertNotNull(employeeData);
        assertEquals(10, employeeData.size());
        assertTrue(employeeData.contains("name20"));
        assertTrue(employeeData.contains("name19"));
        assertTrue(employeeData.contains("name18"));
        assertTrue(employeeData.contains("name17"));
        assertTrue(employeeData.contains("name16"));
        assertTrue(employeeData.contains("name15"));
        assertTrue(employeeData.contains("name14"));
        assertTrue(employeeData.contains("name13"));
        assertTrue(employeeData.contains("name12"));
        assertTrue(employeeData.contains("name11"));
        assertFalse(employeeData.contains("name10"));
        assertFalse(employeeData.contains("name9"));
        assertFalse(employeeData.contains("name8"));
        assertFalse(employeeData.contains("name7"));
        assertFalse(employeeData.contains("name6"));
        assertFalse(employeeData.contains("name5"));
        assertFalse(employeeData.contains("name4"));
        assertFalse(employeeData.contains("name3"));
        assertFalse(employeeData.contains("name2"));
        assertFalse(employeeData.contains("name1"));
    }

    @Test
    void createEmployee_ShouldCreateNewEmployee_Success() {

        Employee newEmployee = new Employee(null, "name21", 555, 55, "title55", "email55");
        CreateEmployeeRequest request = new CreateEmployeeRequest(newEmployee.getName(), newEmployee.getSalary(), newEmployee.getAge(), newEmployee.getTitle(), newEmployee.getEmail());

        when(employeeWebClient.createEmployee(request))
                .thenReturn(new CreateEmployeeResponse(newEmployee, "success"));

        ResponseEntity<Employee> employeesResponse = employeeService.createEmployee(request);

        assertEquals(HttpStatus.OK, employeesResponse.getStatusCode());

        Employee employeeData = employeesResponse.getBody();

        assertNotNull(employeeData);
        assertEquals(newEmployee, employeeData);
    }

    @Test
    void createEmployee_ShouldCreateNewEmployee_InternalServerError() {

        Employee newEmployee = new Employee(null, "name21", 555, 55, "title55", "email55");
        CreateEmployeeRequest request = new CreateEmployeeRequest(newEmployee.getName(), newEmployee.getSalary(), newEmployee.getAge(), newEmployee.getTitle(), newEmployee.getEmail());

        when(employeeWebClient.createEmployee(request)).thenThrow(new RuntimeException("Failed to create employee"));

        ResponseEntity<Employee> employeesResponse = employeeService.createEmployee(request);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, employeesResponse.getStatusCode());

        assertNull(employeesResponse.getBody());
    }

    @Test
    void deleteEmployeeById_ShouldDeleteEmployeeById_Success() {

        String id = employees.get(9).getId().toString();

        when(employeeWebClient.deleteEmployee(id))
                .thenAnswer( iom -> {
                   employees.removeIf(employee -> employee.getId().toString().equals(id));
                   return new DeleteEmployeeResponse(true, "success");
                });

        ResponseEntity<String> response = employeeService.deleteEmployeeById(id);
        String body = response.getBody();

        assertNotNull(body);
        assertEquals("success", body);
        assertEquals(19, employees.size());
    }

    @Test
    void deleteEmployeeById_ShouldDeleteEmployeeById_InternalServerError() {

        String id = employees.get(9).getId().toString();

        when(employeeWebClient.deleteEmployee(id)).thenReturn(null);

        ResponseEntity<String> response = employeeService.deleteEmployeeById(id);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}