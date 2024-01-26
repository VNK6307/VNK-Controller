package ru.netology.vnkcontroller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class VnkControllerApplicationTests {
    @Autowired
    private TestRestTemplate restTemplate;
    @Container
    private static final GenericContainer<?> devApp = new GenericContainer<>("devimage")
            .withExposedPorts(8080);

    @Container
    private static final GenericContainer<?> prodApp = new GenericContainer<>("prodimage")
            .withExposedPorts(8081);

    @Test
    void contextLoads() {
        Integer devAppPort = devApp.getMappedPort(8080);
        Integer prodAppPort = prodApp.getMappedPort(8081);

        ResponseEntity<String> entityDev = restTemplate.getForEntity(
                "http://localhost:" + devAppPort + "/profile", String.class);
        assertEquals("Current profile is dev", entityDev.getBody());


        ResponseEntity<String> entityProd = restTemplate.getForEntity(
                "http://localhost:" + prodAppPort + "/profile", String.class);
        assertEquals("Current profile is production", entityProd.getBody());

        System.out.println("First: " + entityDev.getBody());
        System.out.println("Second: " + entityProd.getBody());
    }
}
