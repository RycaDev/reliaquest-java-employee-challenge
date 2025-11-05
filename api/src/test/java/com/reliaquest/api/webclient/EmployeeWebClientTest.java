package com.reliaquest.api.webclient;

import com.reliaquest.api.model.Employee;
import com.reliaquest.api.response.GetAllEmployeesResponse;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeWebClientTest {

    @Mock
    private WebClient.Builder webClientBuilder;

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    private EmployeeWebClient employeeWebClient;

    private static final String MOCK_SERVER_URL = "http://localhost:8112";
    private static final String EMPLOYEE_PATH = "/api/v1/employee";

    @BeforeEach
    void setUp() {
        when(webClientBuilder.baseUrl(anyString())).thenReturn(webClientBuilder);
        when(webClientBuilder.build()).thenReturn(webClient);

        employeeWebClient = new EmployeeWebClient(webClientBuilder, MOCK_SERVER_URL);
    }

    @Test
    void constructorTest_ShouldBuildWebClient() {
        verify(webClientBuilder).baseUrl(MOCK_SERVER_URL);
        verify(webClientBuilder).build();
    }

    @Test
    void getAllEmployees_ShouldReturnEmployeeResponse_Success() {

        UUID uuid1 = UUID.randomUUID();
        UUID uuid2 = UUID.randomUUID();

        List<Employee> employees = Arrays.asList(
                new Employee(uuid1, "Lakenya Walter", 44444, 44, "title", "email"),
                new Employee(uuid2, "Marcellus Witting", 44444, 44, "title", "email")
        );

        GetAllEmployeesResponse expectedResponse = new GetAllEmployeesResponse(employees, "status");

        mockWebClientChain(expectedResponse);

        GetAllEmployeesResponse actualResponse = employeeWebClient.getAllEmployees();

        assertNotNull(actualResponse);
        assertNotNull(actualResponse.getData());
        assertEquals("Lakenya Walter", actualResponse.getData().get(0).getName());
        assertEquals(44444, actualResponse.getData().get(0).getSalary());
    }

    @Test
    void getAllEmployees_ShouldReturnEmptyList_WhenNoEmployeesFound() {

        GetAllEmployeesResponse expectedResponse = new GetAllEmployeesResponse(Collections.emptyList(), "status");
        mockWebClientChain(expectedResponse);

        GetAllEmployeesResponse actualResponse = employeeWebClient.getAllEmployees();

        assertNotNull(actualResponse);
        assertNotNull(actualResponse.getData());
        assertTrue(actualResponse.getData().isEmpty());
    }

    @Test
    void getAllEmployees_4xxClientError_ShouldThrowRuntimeException() {

        WebClientResponseException clientException = WebClientResponseException.create(
                HttpStatus.NOT_FOUND.value(),
                "Not Found",
                null,
                null,
                null
        );

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(EMPLOYEE_PATH)).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.onStatus(any(Predicate.class), any())).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(GetAllEmployeesResponse.class))
                .thenReturn(Mono.error(clientException));

        assertThrows(WebClientResponseException.class, () -> {
            employeeWebClient.getAllEmployees();
        });
    }

    @Test
    void getAllEmployees_5xxServerError_ShouldThrowRuntimeException() {

        WebClientResponseException serverException = WebClientResponseException.create(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                null,
                null,
                null
        );

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(EMPLOYEE_PATH)).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.onStatus(any(Predicate.class), any())).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(GetAllEmployeesResponse.class))
                .thenReturn(Mono.error(serverException));

        assertThrows(WebClientResponseException.class, () -> {
            employeeWebClient.getAllEmployees();
        });
    }

    private void mockWebClientChain(GetAllEmployeesResponse response) {
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(EMPLOYEE_PATH)).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.onStatus(any(Predicate.class), any())).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(GetAllEmployeesResponse.class))
                .thenReturn(Mono.just(response));
    }

}
