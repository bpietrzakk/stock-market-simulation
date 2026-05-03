package com.bpietrzak.stockmarket;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest // run all spring boot context
@Testcontainers // look for containers and manage their life in this class
public abstract class AbstractIntegrationTest {

    @Container // docker container manage
    @ServiceConnection // automatically configure SpringDataURL to connect
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");
}
