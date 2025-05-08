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
import demo.incident.dto.event.IncidentCreatedEventDto
import demo.incident.dto.event.IncidentStatusChangedEventDto

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
    fun incidentCreatedKafkaTemplate(): KafkaTemplate<String, IncidentCreatedEventDto> =
        KafkaTemplate(producerFactory(IncidentCreatedEventDto::class.java))

    @Bean
    fun incidentStatusChangedKafkaTemplate(): KafkaTemplate<String, IncidentStatusChangedEventDto> =
        KafkaTemplate(producerFactory(IncidentStatusChangedEventDto::class.java))

    @Bean
    fun incidentUpdatedTopic(): NewTopic =
        TopicBuilder.name("incident.updated")
            .partitions(1)
            .replicas(1)
            .build()

    @Bean
    fun incidentCreatedTopic(): NewTopic =
        TopicBuilder.name("incident.created")
            .partitions(1)
            .replicas(1)
            .build()
}
