package demo.incident.kafka

import mu.KotlinLogging
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component
import demo.incident.dto.event.IncidentStatusChangedEventDto
import demo.incident.model.IncidentAuditLog
import demo.incident.repository.IncidentAuditLogRepository

@Component
class IncidentEventConsumer(
    private val auditRepo: IncidentAuditLogRepository
) {
    private val kLogger = KotlinLogging.logger { }

    @KafkaListener(
        topics = ["incident.updated"],
        groupId = "incident-service",
        containerFactory = "incidentStatusChangedKafkaListenerContainerFactory"
    )
    fun handleStatusChange(event: IncidentStatusChangedEventDto) {
        kLogger.info("Received Kafka event on incident.updated: $event")
        val logEntry = IncidentAuditLog(
            incidentId = event.incidentId,
            oldStatus = event.oldStatus.name,
            newStatus = event.newStatus.name
        )
        auditRepo.save(logEntry)
    }
}
