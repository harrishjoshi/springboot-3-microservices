package com.harrishjoshi.microservices.inventory;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MySQLContainer;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class InventoryServiceApplicationTests {

    @ServiceConnection
    static MySQLContainer<?> mysqlContainer = new MySQLContainer<>("mysql:8.3.0");

    @LocalServerPort
    private Integer port;

    @BeforeEach
    void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    static {
        mysqlContainer.start();
    }

    @Test
    void shouldBeInStock() {
        var response = RestAssured.given()
                .queryParam("skuCode", "SKU-1")
                .queryParam("quantity", 1)
                .when()
                .get("/api/v1/inventory")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .response().as(Boolean.class);

        assertTrue(response);
    }

    @Test
    void shouldNotBeInStock() {
        var response = RestAssured.given()
                .queryParam("skuCode", "INVALID-SKU-CODE")
                .queryParam("quantity", 10)
                .when()
                .get("/api/v1/inventory")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .response().as(Boolean.class);

        assertFalse(response);
    }
}
