package com.ratepay.transaction.test.config

import com.example.springdemokotlin.test.EXAMPLE_ONE_LISTENER_ID
import com.example.springdemokotlin.test.EXAMPLE_TWO_LISTENER_ID
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.EventListener
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.config.KafkaListenerEndpointRegistry
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.listener.ContainerProperties
import org.springframework.kafka.listener.MessageListenerContainer
import org.springframework.kafka.test.EmbeddedKafkaBroker
import org.springframework.kafka.test.utils.ContainerTestUtils

@TestConfiguration
class KafkaTestConsumerConfiguration @Autowired constructor(
    val embeddedKafka: EmbeddedKafkaBroker,
    val kafkaListenerEndpointRegistry: KafkaListenerEndpointRegistry,
    @Value("\${spring.embedded.kafka.brokers}") private val bootstrapServers: String,
    @Value("\${example-one.consumer-enabled}") private val exampleOneConsumerEnabled: Boolean,
    @Value("\${example-two.consumer-enabled}") private val exampleTwoConsumerEnabled: Boolean,
) {

    /**
     * needs to be executed AFTER the KafkaListenerEndpoint has been registered to the KafkaListenerEndpointRegistry.
     */
    @EventListener(ContextRefreshedEvent::class)
    fun waitForAssignment() {
        for (messageListenerContainer in kafkaListenerEndpointRegistry.listenerContainers) {
            if (shouldWaitForAssignment(messageListenerContainer)) {
                ContainerTestUtils.waitForAssignment(messageListenerContainer, embeddedKafka.partitionsPerTopic)
            }
        }
    }

    private fun shouldWaitForAssignment(messageListenerContainer: MessageListenerContainer) =
        when (messageListenerContainer.listenerId) {
            EXAMPLE_ONE_LISTENER_ID -> exampleOneConsumerEnabled
            EXAMPLE_TWO_LISTENER_ID -> exampleTwoConsumerEnabled
            else -> true
        }

    @Bean
    fun kafkaTestListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, String> {
        val consumerFactory = DefaultKafkaConsumerFactory(consumerConfigs(), StringDeserializer(), StringDeserializer())
        val containerFactory = ConcurrentKafkaListenerContainerFactory<String, String>()
        containerFactory.consumerFactory = consumerFactory
        containerFactory.containerProperties.ackMode = ContainerProperties.AckMode.MANUAL_IMMEDIATE
        return containerFactory
    }

    private fun consumerConfigs(): Map<String, Any?> {
        return hashMapOf(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to bootstrapServers,
            ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG to false,
        )
    }
}
