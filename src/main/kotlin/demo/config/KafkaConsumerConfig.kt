package qm.demo.config

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.support.serializer.JsonDeserializer
import qm.demo.incident.dto.event.IncidentStatusChangedEventDto

@EnableKafka
@Configuration
class KafkaConsumerConfig {

    @Bean
    fun incidentStatusChangedConsumerFactory(): ConsumerFactory<String, IncidentStatusChangedEventDto> {
        val config = mapOf(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to "localhost:9092",
            ConsumerConfig.GROUP_ID_CONFIG to "incident-service",
            ConsumerConfig.AUTO_OFFSET_RESET_CONFIG to "earliest"
        )

        val deserializer = JsonDeserializer(IncidentStatusChangedEventDto::class.java).apply {
            setRemoveTypeHeaders(false)
            addTrustedPackages("*")
            setUseTypeMapperForKey(true)
        }

        return DefaultKafkaConsumerFactory(config, StringDeserializer(), deserializer)
    }

    @Bean
    fun incidentStatusChangedKafkaListenerContainerFactory():
            ConcurrentKafkaListenerContainerFactory<String, IncidentStatusChangedEventDto> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, IncidentStatusChangedEventDto>()
        factory.consumerFactory = incidentStatusChangedConsumerFactory()
        return factory
    }
}
