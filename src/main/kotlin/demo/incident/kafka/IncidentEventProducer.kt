package demo.incident.kafka

import mu.KotlinLogging
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component
import demo.incident.dto.event.IncidentCreatedEvent
import demo.incident.dto.event.IncidentStatusChangedEvent

@Component
class IncidentEventProducer(
    private val createdTemplate: KafkaTemplate<String, IncidentCreatedEvent>,
    private val statusChangedTemplate: KafkaTemplate<String, IncidentStatusChangedEvent>
) {
    private val kLogger = KotlinLogging.logger {  }

    fun sendCreated(event: IncidentCreatedEvent) {
        val topic = "incident.created"
        kLogger.info("Sending Kafka event to $topic: $event")
        createdTemplate.send(topic, event.id.toString(), event)
    }

    fun sendStatusChanged(event: IncidentStatusChangedEvent) {
        val topic = "incident.updated"
        kLogger.info("Sending Kafka event to $topic: $event")
        statusChangedTemplate.send(topic, event.incidentId.toString(), event)
    }
}
