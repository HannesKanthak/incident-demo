package demo.incident.kafka

import demo.config.KafkaTopics
import demo.incident.dto.event.IncidentCreatedEvent
import demo.incident.dto.event.IncidentStatusChangedEvent
import demo.incident.model.IncidentAuditLog
import demo.incident.repository.IncidentAuditLogRepository
import demo.incident.repository.IncidentRepository
import mu.KotlinLogging
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class IncidentEventConsumer(
    private val auditRepo: IncidentAuditLogRepository,
    private val incidentRepo: IncidentRepository,
) {
    private val kLogger = KotlinLogging.logger { }

    @KafkaListener(
        topics = [KafkaTopics.INCIDENT_UPDATED],
        groupId = "incident-service",
        containerFactory = "incidentStatusChangedKafkaListenerContainerFactory"
    )
    fun handleStatusChange(event: IncidentStatusChangedEvent) {
        kLogger.info("Received Kafka event on incident.updated: $event")
        val incident = incidentRepo.findById(event.incidentId).orElseThrow {
            IllegalArgumentException("Incident ${event.incidentId} not found (in Consumer)")
        }
        val logEntry = IncidentAuditLog(
            incident = incident,
            oldStatus = event.oldStatus.name,
            newStatus = event.newStatus.name
        )
        auditRepo.save(logEntry)
    }

    @KafkaListener(
        topics = [KafkaTopics.INCIDENT_CREATED],
        groupId = "incident-service",
        containerFactory = "incidentCreatedKafkaListenerContainerFactory"
    )
    fun handleIncidentCreated(event: IncidentCreatedEvent) {
        kLogger.info("Received Kafka event on incident.created: $event")
        val incident = incidentRepo.findById(event.id).orElseThrow {
            IllegalArgumentException("Incident ${event.id} not found (in Consumer)")
        }
        val logEntry = IncidentAuditLog(
            incident = incident,
            oldStatus = "-",
            newStatus = "OPEN",
            changedAt = event.createdAt
        )
        auditRepo.save(logEntry)
    }
}
