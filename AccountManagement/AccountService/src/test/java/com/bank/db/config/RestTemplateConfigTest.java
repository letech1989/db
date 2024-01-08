package com.bank.db.config;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class RestTemplateConfigTest {

    @Test
    void restTemplateBeanCreationTest() {
        // Arrange
        RestTemplateConfig restTemplateConfig = new RestTemplateConfig();

        // Act
        RestTemplate restTemplate = restTemplateConfig.restTemplate();

        // Assert
        assertNotNull(restTemplate);
    }
}