package com.example.springdemokotlin.test


import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory

@Configuration
class KafkaProducerConfiguration {

    @Bean
    fun shopSettingsKafkaTemplate(producerFactory: ProducerFactory<String, ExampleDto>) =
        KafkaTemplate(producerFactory)
}
