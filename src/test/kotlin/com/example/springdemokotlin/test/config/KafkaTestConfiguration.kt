package com.example.springdemokotlin.test.config

import org.apache.commons.lang3.RandomStringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.EventListener
import org.springframework.kafka.config.KafkaListenerEndpointRegistry
import org.springframework.kafka.test.utils.ContainerTestUtils
import org.springframework.test.context.support.TestPropertySourceUtils
import org.testcontainers.containers.KafkaContainer
import org.testcontainers.utility.DockerImageName

internal class KafkaTestConfiguration : ApplicationContextInitializer<ConfigurableApplicationContext> {

    companion object {
        private var initialized = false
        private val kafkaContainer = KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.4.3"))

        init {
            if (!initialized) {
                kafkaContainer.start()
                initialized = true
            }
        }
    }

    override fun initialize(configurableApplicationContext: ConfigurableApplicationContext) {
        // each Spring context has its own instance of a KafkaTestConsumer. Each Spring context needs another Kafka consumer group ID, otherwise the created
        // Kafka messages would be spread somehow randomly between the KafkaTestConsumers in the different contexts.
        TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
            configurableApplicationContext,
            "spring.kafka.bootstrap-servers=${kafkaContainer.bootstrapServers}",
            "spring.kafka.consumer.group-id=${createRandomKafkaConsumerGroupId()}",
        )
    }

    private fun createRandomKafkaConsumerGroupId() =
        RandomStringUtils.randomAlphabetic(15)
}

@TestConfiguration
internal class KafkaTestInitializer @Autowired constructor(
    @Suppress("SpringJavaInjectionPointsAutowiringInspection")
    private val kafkaListenerEndpointRegistry: KafkaListenerEndpointRegistry,
) {
    @EventListener(ContextRefreshedEvent::class)
    fun waitForAssignment() {
        for (messageListenerContainer in kafkaListenerEndpointRegistry.listenerContainers) {
            ContainerTestUtils.waitForAssignment(messageListenerContainer, 1)
        }
    }
}
