package com.ratepay.transaction.configuration

import com.ratepay.transaction.messaging.apilogging.ApiLogDto
import com.ratepay.transaction.messaging.correspondencesettings.dto.CorrespondenceSettingsDto
import com.ratepay.transaction.messaging.shopsettings.dto.ShopSettingsDto
import com.ratepay.transaction.messaging.transactioncreation.TransactionCreationRequestDto
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory

@Configuration
class KafkaProducerConfiguration {

    @Bean
    fun shopSettingsKafkaTemplate(producerFactory: ProducerFactory<String, ShopSettingsDto>) =
        KafkaTemplate(producerFactory)

    @Bean
    fun correspondenceSettingsKafkaTemplate(producerFactory: ProducerFactory<String, CorrespondenceSettingsDto>) =
        KafkaTemplate(producerFactory)

    @Bean
    fun transactionKafkaTemplate(producerFactory: ProducerFactory<String, TransactionCreationRequestDto>) =
        KafkaTemplate(producerFactory)

    @Bean
    fun apiLoggingKafkaTemplate(producerFactory: ProducerFactory<String, ApiLogDto>) = KafkaTemplate(producerFactory)
}
