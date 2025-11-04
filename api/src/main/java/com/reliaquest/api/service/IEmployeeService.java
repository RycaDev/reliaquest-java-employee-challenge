package com.reliaquest.api.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface IEmployeeService<Entity, Input> {

    ResponseEntity<List<Entity>> getAllEmployees();

    ResponseEntity<List<Entity>> getEmployeesByNameSearch(@PathVariable String searchString);

    ResponseEntity<Entity> getEmployeeById(@PathVariable String id);

    ResponseEntity<Integer> getHighestSalaryOfEmployees();

    ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames();

    ResponseEntity<Entity> createEmployee(@RequestBody Input employeeInput);

    ResponseEntity<String> deleteEmployeeById(@PathVariable String id);
}
