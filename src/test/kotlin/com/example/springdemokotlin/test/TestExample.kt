package com.example.springdemokotlin.test

import com.example.springdemokotlin.test.config.ComponentTest
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.web.servlet.MockMvc

@ComponentTest
class TestExample @Autowired constructor(
    private val mockMvc: MockMvc,
    private val jdbcTemplate: JdbcTemplate,
) {


    @Test
    fun `test example`() {
    }

}
