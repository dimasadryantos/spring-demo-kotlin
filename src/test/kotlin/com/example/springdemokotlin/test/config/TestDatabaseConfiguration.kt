package com.example.springdemokotlin.test.config

import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.test.context.support.TestPropertySourceUtils
import org.testcontainers.containers.PostgreSQLContainer

/**
 * To have a postgres database initialized for your test annotate your test with [com.ratepay.transaction.test.meta.annotation.TestWithDatabase].
 *
 * example:
 * ```
 * @SpringBootTest
 * @TestWithDatabase
 * class MyServiceTest {
 * }
 * ```
 */
class TestDatabaseConfiguration : ApplicationContextInitializer<ConfigurableApplicationContext> {

    companion object {
        private var initialized = false
        private val postgresqlContainer = PostgreSQLContainer<Nothing>("postgres:13.4")

        init {
            if (!initialized) {
                postgresqlContainer.start()
                initialized = true
            }
        }
    }

    override fun initialize(configurableApplicationContext: ConfigurableApplicationContext) {
        TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
            configurableApplicationContext,
            "spring.datasource.url=" + postgresqlContainer.jdbcUrl,
            "spring.datasource.password=" + postgresqlContainer.password,
            "spring.datasource.username=" + postgresqlContainer.username
        )
    }
}
