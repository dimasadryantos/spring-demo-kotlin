package com.example.springdemokotlin.test

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

const val EXAMPLE_ONE_LISTENER_ID = "example-listener-one"

@Component
class ExampleOneConsumer {
    @KafkaListener(
        containerFactory = KAFKA_JSON_LISTENER_CONTAINER_FACTORY_BEAN,
        id = EXAMPLE_ONE_LISTENER_ID,
        topics = ["\${example-one.topic}"],
        groupId = "\${example-one.group-id}",
        autoStartup = "\${example-one.consumer-enabled}",
    )
    fun consume(message: ExampleDto) {
        println("EXAMPLE CONSUMER ONE ============= $message")
    }
}
