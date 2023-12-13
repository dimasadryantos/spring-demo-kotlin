package com.example.springdemokotlin.test.config

import com.ratepay.transaction.test.config.KafkaTestConsumerConfiguration
import com.ratepay.transaction.test.config.KafkaTestProducerConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.kafka.test.context.EmbeddedKafka
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestPropertySource
import java.lang.annotation.Inherited
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.CLASS

@Target(CLASS)
@Retention(RUNTIME)
@Inherited
@SpringBootTest
@EmbeddedKafka(
    topics = ["example-topic-one", "example-topic-two"],
    controlledShutdown = true,
)
@Import(KafkaTestConsumerConfiguration::class, KafkaTestProducerConfiguration::class)
@ActiveProfiles("test", "kafka-test")
@TestPropertySource(
    properties = [
        "spring.kafka.bootstrap-servers=\${spring.embedded.kafka.brokers}",
        "example-one.consumer-enabled=true",
        "example-two.consumer-enabled=true",
    ],
)
annotation class KafkaSpringBootTest
