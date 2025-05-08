package demo.incident.service

import org.springframework.stereotype.Service
import demo.incident.dto.event.IncidentCreatedEventDto
import demo.incident.dto.api.IncidentRequestDto
import demo.incident.dto.api.IncidentResponseDto
import demo.incident.dto.event.IncidentStatusChangedEventDto
import demo.incident.dto.api.IncidentStatusUpdateDto
import demo.incident.dto.api.toResponseDto
import demo.incident.kafka.IncidentEventProducer
import demo.incident.model.Incident
import demo.incident.model.IncidentStatus
import demo.incident.repository.IncidentRepository
import java.time.LocalDateTime

@Service
class IncidentService(
    private val repository: IncidentRepository,
    private val producer: IncidentEventProducer
) {
    fun createIncident(dto: IncidentRequestDto): IncidentResponseDto {
        val entity = Incident(
            title = dto.title,
            description = dto.description,
            type = dto.type,
            severity = dto.severity,
            status = IncidentStatus.OPEN,
            createdAt = LocalDateTime.now()
        )

        val saved = repository.save(entity)

        producer.sendCreated(
            IncidentCreatedEventDto(
                id = saved.id,
                title = saved.title,
                type = saved.type,
                severity = saved.severity,
                createdAt = saved.createdAt
            )
        )

        return saved.toResponseDto()
    }

    fun listIncidents(): List<IncidentResponseDto> {
        return repository.findAll().map(Incident::toResponseDto)
    }

    fun updateStatus(id: Long, dto: IncidentStatusUpdateDto): IncidentResponseDto {
        val entity = repository.findById(id).orElseThrow {
            IllegalArgumentException("Incident $id not found")
        }
        val oldStatus = entity.status
        val newStatus = dto.status

        if (oldStatus == newStatus) {
            return entity.toResponseDto()
        }

        if (oldStatus == IncidentStatus.RESOLVED) {
            throw IllegalStateException("Cannot update status from RESOLVED")
        }
        entity.status = newStatus
        entity.updatedAt = LocalDateTime.now()

        val saved = repository.save(entity)

        producer.sendStatusChanged(
            IncidentStatusChangedEventDto(
                incidentId = id,
                oldStatus = oldStatus,
                newStatus = newStatus,
                changedAt = saved.updatedAt!!
            )
        )

        return saved.toResponseDto()
    }
}
