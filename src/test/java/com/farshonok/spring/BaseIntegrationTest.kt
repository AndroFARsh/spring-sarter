package com.farshonok.spring

import com.farshonok.spring.annotations.IT
import org.junit.jupiter.api.BeforeAll
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.context.jdbc.Sql
import org.testcontainers.containers.PostgreSQLContainer

@Sql(scripts = [
    "classpath:db/data.sql",
])
@IT
abstract class BaseIntegrationTest {

    companion object {
        val container = PostgreSQLContainer<Nothing>("postgres:latest")

        @BeforeAll
        @JvmStatic
        fun runContainer() {
            container.start()
        }

        @DynamicPropertySource
        @JvmStatic
        fun postgresProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", container::getJdbcUrl);
        }
    }
}