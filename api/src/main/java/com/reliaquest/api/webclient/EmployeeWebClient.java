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

@Slf4j
@Service
public class EmployeeWebClient implements IEmployeeWebClient {

    private final WebClient webClient;
    private static final String EMPLOYEE_PATH = "/api/v1/employee";

    public EmployeeWebClient(WebClient.Builder webClientBuilder,
                             @Value("${mock.server.url}")  String mockServerUrl) {
        this.webClient = webClientBuilder
                .baseUrl(mockServerUrl)
                .build();
    }

    @Override
    public GetAllEmployeesResponse getAllEmployees() {
        return webClient.get()
                .uri(EMPLOYEE_PATH)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> {
                    log.error(response.toString());
                    return Mono.error(new RuntimeException("Client error: " + response.statusCode()));
                })
                .onStatus(HttpStatusCode::is5xxServerError, response -> {
                    log.error(response.toString());
                    return Mono.error(new RuntimeException("Server error: " + response.statusCode()));
                })
                .bodyToMono(GetAllEmployeesResponse.class)
                .block();
    }

    @Override
    public GetEmployeeResponse getEmployee(UUID id) {
        return null;
    }

    @Override
    public UpdateEmployeeResponse updateEmployee(Employee employee) {
        return null;
    }

    @Override
    public DeleteEmployeeResponse deleteEmployee(UUID id) {
        return null;
    }
}
