package demo.incident.kafka

import demo.config.KafkaTopics
import demo.incident.dto.event.IncidentCreatedEvent
import demo.incident.model.IncidentSeverity
import demo.incident.model.IncidentType
import io.kotest.core.spec.style.StringSpec
import io.mockk.mockk
import io.mockk.verify
import org.springframework.kafka.core.KafkaTemplate
import java.time.LocalDateTime

class IncidentEventProducerTest : StringSpec({

    "should send created event via Kafka" {
        val kafkaTemplate = mockk<KafkaTemplate<String, IncidentCreatedEvent>>(relaxed = true)
        val producer = IncidentEventProducer(createdTemplate = kafkaTemplate, statusChangedTemplate = mockk(relaxed = true))

        val event = IncidentCreatedEvent(
            id = 1L,
            title = "Test",
            type = IncidentType.AUDIT_FINDING,
            severity = IncidentSeverity.HIGH,
            createdAt = LocalDateTime.now()
        )

        producer.sendCreated(event)

        verify {
            kafkaTemplate.send(KafkaTopics.INCIDENT_CREATED, event.id.toString(), event)
        }
    }
})