package com.example.springdemokotlin.test.config

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

@TestWithDatabase
@AutoConfigureMockMvc
@ContextConfiguration(initializers = [TestDatabaseConfiguration::class, KafkaTestConfiguration::class])
@SpringBootTest
@KafkaSpringBootTest
annotation class ComponentTest
