package demo.incident.service

import demo.incident.dto.api.*
import demo.incident.dto.event.IncidentCreatedEvent
import demo.incident.dto.event.IncidentStatusChangedEvent
import demo.incident.kafka.IncidentEventProducer
import demo.incident.model.Incident
import demo.incident.model.IncidentStatus
import demo.incident.model.IncidentStatusTransitions
import demo.incident.repository.IncidentRepository
import demo.incident.repository.IncidentSpecifications
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class IncidentService(
    private val repository: IncidentRepository,
    private val producer: IncidentEventProducer
) {
    @Transactional
    fun createIncident(dto: IncidentRequest): IncidentResponse {
        val now = LocalDateTime.now()
        val incident = Incident(
            title = dto.title,
            description = dto.description,
            type = dto.type,
            severity = dto.severity,
            status = IncidentStatus.OPEN,
            createdAt = now,
            updatedAt = now
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

    fun search(params: IncidentSearchParams): Page<IncidentResponse> {
        val spec = IncidentSpecifications.withFilters(
            params.status,
            params.type,
            params.severity,
            params.query
        )
        val sort = Sort.by(params.direction, params.sortBy)
        val pageable = PageRequest.of(
            params.page,
            params.size,
            sort
        )
        return repository
            .findAll(spec, pageable)
            .map { incident -> incident.toResponseDto() }
    }

    @Transactional
    fun updateStatus(id: Long, update: IncidentStatusUpdate): IncidentResponse {
        val incident = repository.findById(id).orElseThrow {
            IllegalArgumentException("Incident $id not found")
        }
        val oldStatus = incident.status
        val newStatus = update.status
        if (oldStatus == newStatus) {
            return incident.toResponseDto()
        }

        if (!IncidentStatusTransitions.isValidTransition(oldStatus, newStatus)) {
            throw IllegalStateException("Cannot update status from $oldStatus to $newStatus")
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
