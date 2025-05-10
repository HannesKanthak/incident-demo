package demo.incident.dto.event

import demo.incident.model.IncidentStatus
import java.time.LocalDateTime

data class IncidentStatusChangedEvent(
    val incidentId: Long,
    val oldStatus: IncidentStatus,
    val newStatus: IncidentStatus,
    val changedAt: LocalDateTime = LocalDateTime.now()
)
