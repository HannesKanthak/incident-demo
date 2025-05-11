package demo.config

import org.apache.kafka.clients.admin.NewTopic
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.TopicBuilder
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.support.serializer.JsonSerializer
import demo.incident.dto.event.IncidentCreatedEvent
import demo.incident.dto.event.IncidentStatusChangedEvent

@Configuration
class KafkaProducerConfig {

    private val bootstrapServers = "localhost:9092"

    private fun <T> producerFactory(valueType: Class<T>): ProducerFactory<String, T> {
        val config = mapOf(
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to bootstrapServers,
            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to JsonSerializer::class.java
        )
        return DefaultKafkaProducerFactory(config)
    }

    @Bean
    fun incidentCreatedKafkaTemplate(): KafkaTemplate<String, IncidentCreatedEvent> =
        KafkaTemplate(producerFactory(IncidentCreatedEvent::class.java))

    @Bean
    fun incidentStatusChangedKafkaTemplate(): KafkaTemplate<String, IncidentStatusChangedEvent> =
        KafkaTemplate(producerFactory(IncidentStatusChangedEvent::class.java))

    @Bean
    fun incidentUpdatedTopic(): NewTopic =
        TopicBuilder.name(KafkaTopics.INCIDENT_UPDATED)
            .partitions(1)
            .replicas(1)
            .build()

    @Bean
    fun incidentCreatedTopic(): NewTopic =
        TopicBuilder.name(KafkaTopics.INCIDENT_CREATED)
            .partitions(1)
            .replicas(1)
            .build()
}
