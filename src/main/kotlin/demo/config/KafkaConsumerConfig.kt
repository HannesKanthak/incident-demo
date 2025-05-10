package demo.config

import demo.incident.dto.event.IncidentCreatedEvent
import demo.incident.dto.event.IncidentStatusChangedEvent
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.support.serializer.JsonDeserializer

@EnableKafka
@Configuration
class KafkaConsumerConfig {

    @Bean
    fun incidentStatusChangedConsumerFactory(): ConsumerFactory<String, IncidentStatusChangedEvent> {
        val config = mapOf(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to "localhost:9092",
            ConsumerConfig.GROUP_ID_CONFIG to "incident-service",
            ConsumerConfig.AUTO_OFFSET_RESET_CONFIG to "earliest"
        )

        val deserializer = JsonDeserializer(IncidentStatusChangedEvent::class.java).apply {
            setRemoveTypeHeaders(false)
            addTrustedPackages("demo.incident.dto.event")
            setUseTypeMapperForKey(true)
        }

        return DefaultKafkaConsumerFactory(
            config,
            StringDeserializer(),
            deserializer
        )
    }

    @Bean
    fun incidentStatusChangedKafkaListenerContainerFactory():
            ConcurrentKafkaListenerContainerFactory<String, IncidentStatusChangedEvent> =
        ConcurrentKafkaListenerContainerFactory<String, IncidentStatusChangedEvent>().apply {
            consumerFactory = incidentStatusChangedConsumerFactory()
        }


    @Bean
    fun incidentCreatedConsumerFactory(): ConsumerFactory<String, IncidentCreatedEvent> {
        val config = mapOf(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to "localhost:9092",
            ConsumerConfig.GROUP_ID_CONFIG to "incident-service",
            ConsumerConfig.AUTO_OFFSET_RESET_CONFIG to "earliest"
        )

        val deserializer = JsonDeserializer(IncidentCreatedEvent::class.java).apply {
            setRemoveTypeHeaders(false)
            addTrustedPackages("demo.incident.dto.event")
            setUseTypeMapperForKey(true)
        }

        return DefaultKafkaConsumerFactory(
            config,
            StringDeserializer(),
            deserializer
        )
    }

    @Bean
    fun incidentCreatedKafkaListenerContainerFactory():
            ConcurrentKafkaListenerContainerFactory<String, IncidentCreatedEvent> =
        ConcurrentKafkaListenerContainerFactory<String, IncidentCreatedEvent>().apply {
            consumerFactory = incidentCreatedConsumerFactory()
        }
}
