package demo.incident.dto.event

import demo.incident.model.IncidentSeverity
import demo.incident.model.IncidentType
import java.time.LocalDateTime

data class IncidentCreatedEvent(
    val id: Long,
    val title: String,
    val type: IncidentType,
    val severity: IncidentSeverity,
    val createdAt: LocalDateTime
)
