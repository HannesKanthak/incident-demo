package demo.incident.dto.event

import demo.incident.model.IncidentStatus
import java.time.LocalDateTime

data class IncidentStatusChangedEventDto(
    val incidentId: Long,
    val oldStatus: IncidentStatus,
    val newStatus: IncidentStatus,
    val changedAt: LocalDateTime = LocalDateTime.now()
)
