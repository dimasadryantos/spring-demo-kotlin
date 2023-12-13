package com.example.springdemokotlin.test.config

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.boot.test.context.TestComponent
import org.springframework.context.annotation.Profile
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment

@Profile("test")
@TestComponent
class KafkaConsumersStub {

    val receivedTransactionCreationRecords: MutableList<ConsumerRecord<String, String>> = mutableListOf()
    val receivedShopSettingsRecords: MutableList<ConsumerRecord<String, String>> = mutableListOf()
    val receivedPartnerConfigurationRecords: MutableList<ConsumerRecord<String, String>> = mutableListOf()
    val receivedCorrespondenceSettingsRecords: MutableList<ConsumerRecord<String, String>> = mutableListOf()
    val receivedApiLoggingRecords: MutableList<ConsumerRecord<String, String>> = mutableListOf()


    @KafkaListener(
        containerFactory = "kafkaTestListenerContainerFactory",
        topics = ["example-topic-one"],
        groupId = "stub"
    )
    fun onShopSettingsMessage(record: ConsumerRecord<String, String>, acknowledgment: Acknowledgment) {
        receivedShopSettingsRecords += record
        acknowledgment.acknowledge()
    }

    @KafkaListener(
        containerFactory = "kafkaTestListenerContainerFactory",
        topics = ["example-topic-two"],
        groupId = "stub"
    )
    fun onPartnerConfigMessage(record: ConsumerRecord<String, String>, acknowledgment: Acknowledgment) {
        receivedPartnerConfigurationRecords += record
        acknowledgment.acknowledge()
    }
}
