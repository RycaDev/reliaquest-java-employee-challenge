package com.reliaquest.api.service;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IEmployeeService<Entity> {

    ResponseEntity<List<Entity>> getAllEmployees();

}
