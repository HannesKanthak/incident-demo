package demo.incident.service

import demo.incident.dto.api.IncidentRequest
import demo.incident.dto.api.IncidentResponse
import demo.incident.dto.api.IncidentStatusUpdate
import demo.incident.dto.api.toResponseDto
import demo.incident.dto.event.IncidentCreatedEvent
import demo.incident.dto.event.IncidentStatusChangedEvent
import demo.incident.kafka.IncidentEventProducer
import demo.incident.model.Incident
import demo.incident.model.IncidentStatus
import demo.incident.repository.IncidentRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class IncidentService(
    private val repository: IncidentRepository,
    private val producer: IncidentEventProducer
) {
    fun createIncident(dto: IncidentRequest): IncidentResponse {
        val incident = Incident(
            title = dto.title,
            description = dto.description,
            type = dto.type,
            severity = dto.severity,
            status = IncidentStatus.OPEN,
            createdAt = LocalDateTime.now()
        )

        val saved = repository.save(incident)

        producer.sendCreated(
            IncidentCreatedEvent(
                id = saved.id,
                title = saved.title,
                type = saved.type,
                severity = saved.severity,
                createdAt = saved.createdAt
            )
        )

        return saved.toResponseDto()
    }

    fun listIncidents(): List<IncidentResponse> =
        repository.findAll().map(Incident::toResponseDto)

    fun updateStatus(id: Long, update: IncidentStatusUpdate): IncidentResponse {
        val incident = repository.findById(id).orElseThrow {
            IllegalArgumentException("Incident $id not found")
        }
        val oldStatus = incident.status
        val newStatus = update.status
        if (oldStatus == newStatus) {
            return incident.toResponseDto()
        }

        if (oldStatus == IncidentStatus.RESOLVED) {
            throw IllegalStateException("Cannot update status from RESOLVED")
        }
        incident.apply {
            status = newStatus
            updatedAt = LocalDateTime.now()
        }

        val saved = repository.save(incident)

        producer.sendStatusChanged(
            IncidentStatusChangedEvent(
                incidentId = id,
                oldStatus = oldStatus,
                newStatus = newStatus,
                changedAt = saved.updatedAt
            )
        )

        return saved.toResponseDto()
    }
}
