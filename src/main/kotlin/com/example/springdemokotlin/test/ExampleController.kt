package com.example.springdemokotlin.test


import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class ExampleController {

    @GetMapping(value = ["/validURI/{id}"])
    fun exampleMethod(@PathVariable id: Long) {
        println(" COME IN ========================= ")
    }
}