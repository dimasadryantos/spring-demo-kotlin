package com.ratepay.transaction.test.config

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.support.serializer.JsonSerializer

@TestConfiguration
class KafkaTestProducerConfiguration @Autowired constructor(
    @Value("\${spring.embedded.kafka.brokers}")
    private val bootstrapServers: String,
) {

    @Bean("kafkaTestTemplate")
    fun kafkaTestTemplate(): KafkaTemplate<String, String>? {
        return KafkaTemplate(producerFactory<String>())
    }

    private fun <T> producerFactory(): ProducerFactory<String, T> {
        return DefaultKafkaProducerFactory(producerConfiguration())
    }

    private fun producerConfiguration(): Map<String, Any> {
        val props: MutableMap<String, Any> = HashMap()
        props[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapServers
        props[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        props[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        props[ProducerConfig.ACKS_CONFIG] = "all"
        props[JsonSerializer.ADD_TYPE_INFO_HEADERS] = false
        return props
    }
}
