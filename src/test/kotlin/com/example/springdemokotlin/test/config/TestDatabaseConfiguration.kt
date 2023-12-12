package com.example.springdemokotlin.test.config

import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.testcontainers.containers.PostgreSQLContainer

internal class TestDatabaseConfiguration : ApplicationContextInitializer<ConfigurableApplicationContext> {
    companion object {
        private var initialized = false
        private val postgresqlContainer = PostgreSQLContainer<Nothing>("postgres:13.7")

        init {
            if (!initialized) {
                postgresqlContainer.start()
                initialized = true
            }
        }
    }

    override fun initialize(configurableApplicationContext: ConfigurableApplicationContext) {
    }
}
