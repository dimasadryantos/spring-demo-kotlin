package com.example.springdemokotlin.test

import com.fasterxml.jackson.annotation.JsonInclude.Include.NON_ABSENT
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE
import com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SNAKE_CASE
import com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

const val KAFKA_OBJECT_MAPPER_QUALIFIER: String = "kafkaObjectMapper"
private const val CACHE_SIZE = 512

@Configuration
class JacksonConfiguration {
    @Qualifier(KAFKA_OBJECT_MAPPER_QUALIFIER)
    @Bean
    fun kafkaObjectMapper() = createObjectMapperCommon()

    private fun createObjectMapperCommon(): ObjectMapper {
        val objectMapper = ObjectMapper()

        objectMapper.propertyNamingStrategy = SNAKE_CASE

        objectMapper.setSerializationInclusion(NON_ABSENT)

        objectMapper.disable(WRITE_DATES_AS_TIMESTAMPS)
        objectMapper.disable(ADJUST_DATES_TO_CONTEXT_TIME_ZONE)

        objectMapper.disable(FAIL_ON_UNKNOWN_PROPERTIES)

        objectMapper.registerModule(createAndConfigureJavaTimeModule())
        objectMapper.registerModule(
            KotlinModule.Builder()
                .withReflectionCacheSize(CACHE_SIZE)
                .configure(KotlinFeature.NullToEmptyCollection, false)
                .configure(KotlinFeature.NullToEmptyMap, false)
                .configure(KotlinFeature.NullIsSameAsDefault, false)
                .configure(KotlinFeature.SingletonSupport, false)
                .configure(KotlinFeature.StrictNullChecks, false)
                .build(),
        )
        return objectMapper
    }

    private fun createAndConfigureJavaTimeModule(): JavaTimeModule {
        val javaTimeModule = JavaTimeModule()
        javaTimeModule.addSerializer(OffsetDateTime::class.java, OffsetDateTimeSerializerWithMilliSeconds())
        return javaTimeModule
    }
}

class OffsetDateTimeSerializerWithMilliSeconds : JsonSerializer<OffsetDateTime?>() {
    private val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")

    override fun serialize(
        instant: OffsetDateTime?,
        jsonGenerator: JsonGenerator,
        serializerProvider: SerializerProvider?,
    ) {
        val serializedInstant = dateTimeFormatter.format(instant)
        jsonGenerator.writeString(serializedInstant)
    }
}
