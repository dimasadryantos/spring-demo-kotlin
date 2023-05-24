package com.example.springdemokotlin.test

import com.example.springdemokotlin.test.config.TestDatabaseConfiguration
import com.example.springdemokotlin.test.config.TestWithDatabase
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

@TestWithDatabase
@AutoConfigureMockMvc
@ContextConfiguration(initializers = [TestDatabaseConfiguration::class])
@SpringBootTest
annotation class ComponentTest
