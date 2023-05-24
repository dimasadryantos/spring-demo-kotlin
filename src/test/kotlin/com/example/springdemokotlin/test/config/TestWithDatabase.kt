package com.example.springdemokotlin.test.config


import java.lang.annotation.Inherited
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Inherited
@ContextConfiguration(initializers = [TestDatabaseConfiguration::class])
@CleanDatabaseAfterEachTest
@ActiveProfiles("local")
annotation class TestWithDatabase
