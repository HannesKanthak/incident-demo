package qm.demo.incident.dto.event

import qm.demo.incident.model.IncidentSeverity
import qm.demo.incident.model.IncidentType
import java.time.LocalDateTime

data class IncidentCreatedEventDto(
    val id: Long,
    val title: String,
    val type: IncidentType,
    val severity: IncidentSeverity,
    val createdAt: LocalDateTime
)
