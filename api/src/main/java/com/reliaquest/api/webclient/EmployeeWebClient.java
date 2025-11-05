package com.reliaquest.api.webclient;

import com.reliaquest.api.exceptions.RetryableException;
import com.reliaquest.api.request.CreateEmployeeRequest;
import com.reliaquest.api.request.DeleteEmployeeRequest;
import com.reliaquest.api.response.CreateEmployeeResponse;
import com.reliaquest.api.response.DeleteEmployeeResponse;
import com.reliaquest.api.response.GetAllEmployeesResponse;
import com.reliaquest.api.response.GetEmployeeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

@Slf4j
@Service
public class EmployeeWebClient implements IEmployeeWebClient {

    private final WebClient webClient;
    private static final String EMPLOYEE_PATH = "/api/v1/employee";
    private static final String SLASH = "/";
    private static final int MAX_RETRIES = 30;
    private static final int MAX_DURATION = 10;
    private static final int DURATION = 1;

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

                    if (response.statusCode().value() == 429) {
                        return Mono.error(new RetryableException("Hit Rate Limiter"));
                    }

                    log.error(response.toString());
                    return Mono.error(new RuntimeException("Client error: " + response.statusCode()));
                })
                .onStatus(HttpStatusCode::is5xxServerError, response -> {
                    log.error(response.toString());
                    return Mono.error(new RuntimeException("Server error: " + response.statusCode()));
                })
                .bodyToMono(GetAllEmployeesResponse.class)
                .retryWhen(Retry.backoff(MAX_RETRIES, Duration.ofSeconds(DURATION))
                        .maxBackoff(Duration.ofSeconds(MAX_DURATION))
                        .filter(throwable -> throwable instanceof RetryableException)
                        .doBeforeRetry( retry -> log.warn("Retrying getAllEmployees due to rate limiter, attempt #{}", retry.totalRetries() + 1)))
                .block();
    }

    @Override
    public GetEmployeeResponse getEmployeeById(String id) {

        return webClient.get()
                .uri(EMPLOYEE_PATH + SLASH + id)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> {

                    if (response.statusCode().value() == 429) {
                        return Mono.error(new RetryableException("Hit Rate Limiter"));
                    }

                    log.error(response.toString());
                    return Mono.error(new RuntimeException("Client error: " + response.statusCode()));
                })
                .onStatus(HttpStatusCode::is5xxServerError, response -> {
                    log.error(response.toString());
                    return Mono.error(new RuntimeException("Server error: " + response.statusCode()));
                })
                .bodyToMono(GetEmployeeResponse.class)
                .retryWhen(Retry.backoff(MAX_RETRIES, Duration.ofSeconds(DURATION))
                        .maxBackoff(Duration.ofSeconds(MAX_DURATION))
                        .filter(throwable -> throwable instanceof RetryableException)
                        .doBeforeRetry( retry -> log.warn("Retrying getEmployeeById due to rate limiter, attempt #{}", retry.totalRetries() + 1)))
                .block();
    }

    @Override
    public CreateEmployeeResponse createEmployee(CreateEmployeeRequest createEmployeeRequest) {

        return webClient.post()
                .uri(EMPLOYEE_PATH)
                .bodyValue(createEmployeeRequest)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> {

                    if (response.statusCode().value() == 429) {
                        return Mono.error(new RetryableException("Hit Rate Limiter"));
                    }

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
                .retryWhen(Retry.backoff(MAX_RETRIES, Duration.ofSeconds(DURATION))
                        .maxBackoff(Duration.ofSeconds(MAX_DURATION))
                        .filter(throwable -> throwable instanceof RetryableException)
                        .doBeforeRetry( retry -> log.warn("Retrying createEmployee due to rate limiter, attempt #{}", retry.totalRetries() + 1)))
                .block();
    }

    @Override
    public DeleteEmployeeResponse deleteEmployee(String name) {

        DeleteEmployeeRequest deleteEmployeeRequest = new DeleteEmployeeRequest(name);

        return webClient.method(HttpMethod.DELETE)
                .uri(EMPLOYEE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(deleteEmployeeRequest)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> {

                    if (response.statusCode().value() == 429) {
                        return Mono.error(new RetryableException("Hit Rate Limiter"));
                    }

                    log.error(response.toString());
                    return Mono.error(new RuntimeException("Client error: " + response.statusCode()));
                })
                .onStatus(HttpStatusCode::is5xxServerError, response -> {
                    log.error(response.toString());
                    return Mono.error(new RuntimeException("Server error: " + response.statusCode()));
                })
                .bodyToMono(DeleteEmployeeResponse.class)
                .retryWhen(Retry.backoff(MAX_RETRIES, Duration.ofSeconds(DURATION))
                        .maxBackoff(Duration.ofSeconds(MAX_DURATION))
                        .filter(throwable -> throwable instanceof RetryableException)
                        .doBeforeRetry( retry -> log.warn("Retrying deleteEmployee due to rate limiter, attempt #{}", retry.totalRetries() + 1)))
                .block();
    }
}
