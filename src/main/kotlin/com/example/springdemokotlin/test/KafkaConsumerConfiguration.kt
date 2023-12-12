package com.example.springdemokotlin.test

import com.fasterxml.jackson.databind.ObjectMapper
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.listener.ConsumerRecordRecoverer
import org.springframework.kafka.listener.DefaultErrorHandler
import org.springframework.kafka.support.converter.StringJsonMessageConverter
import org.springframework.util.backoff.FixedBackOff

const val KAFKA_JSON_LISTENER_CONTAINER_FACTORY_BEAN = "kafkaListenerContainerFactory"
const val KAFKA_RAW_LISTENER_CONTAINER_FACTORY_BEAN = "kafkaRawListenerContainerFactory"

private val log = KotlinLogging.logger { }

@EnableKafka
@Configuration
class KafkaConsumerConfiguration {

    @Bean(KAFKA_JSON_LISTENER_CONTAINER_FACTORY_BEAN)
    fun kafkaListenerContainerFactory(
        consumerFactory: ConsumerFactory<String, String>,
        @Qualifier(KAFKA_OBJECT_MAPPER_QUALIFIER) objectMapper: ObjectMapper,
        @Value("\${kafka.client.backoffperiod}") backOffPeriod: Long,
        @Value("\${kafka.client.maxAttempts}") maxAttempts: Long,
    ): ConcurrentKafkaListenerContainerFactory<String, String> {
        val factory = createBaseFactory(consumerFactory, backOffPeriod, maxAttempts)
        factory.setRecordMessageConverter(StringJsonMessageConverter(objectMapper))

        return factory
    }

    @Bean(KAFKA_RAW_LISTENER_CONTAINER_FACTORY_BEAN)
    fun kafkaRawListenerContainerFactory(
        consumerFactory: ConsumerFactory<String, String>,
        @Value("\${kafka.client.backoffperiod}") backOffPeriod: Long,
        @Value("\${kafka.client.maxAttempts}") maxAttempts: Long,
    ) = createBaseFactory(consumerFactory, backOffPeriod, maxAttempts)

    private fun createBaseFactory(
        consumerFactory: ConsumerFactory<String, String>,
        backOffPeriod: Long,
        maxAttempts: Long,
    ): ConcurrentKafkaListenerContainerFactory<String, String> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, String>()
        factory.consumerFactory = consumerFactory
        factory.setCommonErrorHandler(createErrorHandler(backOffPeriod, maxAttempts))

        return factory
    }

    private fun createErrorHandler(backOffPeriod: Long, maxAttempts: Long): DefaultErrorHandler {
        val recoverer = ConsumerRecordRecoverer { record, exception ->
            log.error(exception) { "error processing record, JSON:\n${record.value()}" }
        }
        return DefaultErrorHandler(recoverer, FixedBackOff(backOffPeriod, maxAttempts - 1L))
    }
}
