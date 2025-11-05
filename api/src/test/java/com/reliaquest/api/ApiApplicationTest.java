package com.reliaquest.api;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApiApplicationTest {

    @Test
    void contextLoads() {}

    @Test
    void main_ShouldStartApplication() {
        assertDoesNotThrow(() -> {
            ApiApplication.main(new String[] {});
        });
    }
}
