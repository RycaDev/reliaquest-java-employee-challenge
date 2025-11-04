package com.reliaquest.api.webclient;

import com.reliaquest.api.request.CreateEmployeeRequest;
import com.reliaquest.api.response.CreateEmployeeResponse;
import com.reliaquest.api.response.DeleteEmployeeResponse;
import com.reliaquest.api.response.GetAllEmployeesResponse;
import com.reliaquest.api.response.GetEmployeeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class EmployeeWebClient implements IEmployeeWebClient {

    private final WebClient webClient;
    private static final String EMPLOYEE_PATH = "/api/v1/employee";
    private static final String SLASH = "/";

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
    public GetEmployeeResponse getEmployeeById(String id) {

        return webClient.get()
                .uri(EMPLOYEE_PATH + SLASH + id)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> {
                    log.error(response.toString());
                    return Mono.error(new RuntimeException("Client error: " + response.statusCode()));
                })
                .onStatus(HttpStatusCode::is5xxServerError, response -> {
                    log.error(response.toString());
                    return Mono.error(new RuntimeException("Server error: " + response.statusCode()));
                })
                .bodyToMono(GetEmployeeResponse.class)
                .block();
    }

    @Override
    public CreateEmployeeResponse createEmployee(CreateEmployeeRequest createEmployeeRequest) {

        return webClient.post()
                .uri(EMPLOYEE_PATH)
                .bodyValue(createEmployeeRequest)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> {
                    log.error("Error creating employee (4xx): {}", response.statusCode());
                    return Mono.error(new RuntimeException("Client error: " + response.statusCode()));
                })
                .onStatus(HttpStatusCode::is5xxServerError, response -> {
                    log.error("Error creating employee (5xx): {}", response.statusCode());
                    return Mono.error(new RuntimeException("Server error: " + response.statusCode()));
                })
                .bodyToMono(CreateEmployeeResponse.class)
                .doOnSuccess(response -> log.info("Successfully created employee with ID: {}",
                        response != null && response.getData() != null ? response.getData().getId() : "unknown"))
                .block();
    }

    @Override
    public DeleteEmployeeResponse deleteEmployee(String id) {

        return webClient.delete()
                .uri(EMPLOYEE_PATH + SLASH + id)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> {
                    log.error(response.toString());
                    return Mono.error(new RuntimeException("Client error: " + response.statusCode()));
                })
                .onStatus(HttpStatusCode::is5xxServerError, response -> {
                    log.error(response.toString());
                    return Mono.error(new RuntimeException("Server error: " + response.statusCode()));
                })
                .bodyToMono(DeleteEmployeeResponse.class)
                .block();
    }
}
