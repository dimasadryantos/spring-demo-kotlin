package com.example.springdemokotlin.test.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.TestComponent
import org.springframework.context.annotation.Import
import org.springframework.context.event.EventListener
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.event.AfterTestMethodEvent
import org.springframework.test.jdbc.JdbcTestUtils
import java.lang.annotation.Inherited

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Inherited
@Import(DatabaseCleaner::class)
annotation class CleanDatabaseAfterEachTest

@TestComponent
class DatabaseCleaner @Autowired constructor(private val jdbcTemplate: JdbcTemplate) {

    @EventListener
    fun deleteFromAllTables(event: AfterTestMethodEvent) {
        JdbcTestUtils.deleteFromTables(
            jdbcTemplate,
        )
    }
}
