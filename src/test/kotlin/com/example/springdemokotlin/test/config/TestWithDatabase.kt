package com.example.springdemokotlin.test.config

import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import java.lang.annotation.Inherited

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Inherited
@ContextConfiguration(initializers = [TestDatabaseConfiguration::class])
@CleanDatabaseAfterEachTest
@ActiveProfiles("local")
annotation class TestWithDatabase
