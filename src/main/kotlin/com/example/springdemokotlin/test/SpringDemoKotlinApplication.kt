package com.example.springdemokotlin.test

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringDemoKotlinApplication

fun main(args: Array<String>) {
    runApplication<SpringDemoKotlinApplication>(*args)
}
