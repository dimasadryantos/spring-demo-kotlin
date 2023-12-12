package com.example.springdemokotlin.test

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

const val EXAMPLE_TWO_LISTENER_ID = "example-two-listener"

@Component
class ExampleTwoConsumer {
    @KafkaListener(
        containerFactory = KAFKA_RAW_LISTENER_CONTAINER_FACTORY_BEAN,
        id = EXAMPLE_TWO_LISTENER_ID,
        topics = ["\${example-two.topic}"],
        groupId = "\${example-two.group-id}",
        autoStartup = "\${example-two.consumer-enabled}",
    )
    fun consume(rawEvent: String) {
        println("EXAMPLE TWO CONSUMER $rawEvent")
    }
}
