package demo.incident.kafka

import mu.KotlinLogging
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component
import demo.incident.dto.event.IncidentCreatedEventDto
import demo.incident.dto.event.IncidentStatusChangedEventDto

@Component
class IncidentEventProducer(
    private val createdTemplate: KafkaTemplate<String, IncidentCreatedEventDto>,
    private val statusChangedTemplate: KafkaTemplate<String, IncidentStatusChangedEventDto>
) {
    private val kLogger = KotlinLogging.logger {  }

    fun sendCreated(event: IncidentCreatedEventDto) {
        val topic = "incident.created"
        kLogger.info("Sending Kafka event to $topic: $event")
        createdTemplate.send(topic, event.id.toString(), event)
    }

    fun sendStatusChanged(event: IncidentStatusChangedEventDto) {
        val topic = "incident.updated"
        kLogger.info("Sending Kafka event to $topic: $event")
        statusChangedTemplate.send(topic, event.incidentId.toString(), event)
    }
}
