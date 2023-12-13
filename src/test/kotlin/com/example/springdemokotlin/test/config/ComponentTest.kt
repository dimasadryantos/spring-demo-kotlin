package com.example.springdemokotlin.test.config

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.context.annotation.Import
import org.springframework.test.context.ContextConfiguration

@KafkaSpringBootTest
@TestWithDatabase
@AutoConfigureMockMvc
@Import(KafkaConsumersStub::class)
@ContextConfiguration(initializers = [TestDatabaseConfiguration::class])
annotation class ComponentTest
