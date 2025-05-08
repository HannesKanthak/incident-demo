package qm.demo.incident.dto.api

import qm.demo.incident.model.Incident
import qm.demo.incident.model.IncidentStatus
import java.time.LocalDateTime

data class IncidentResponseDto(
    val id: Long,
    val title: String,
    val description: String,
    val type: String,
    val severity: String,
    val status: IncidentStatus,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime?
)

fun Incident.toResponseDto() = IncidentResponseDto(
    id = id,
    title = title,
    description = description,
    type = type.name,
    severity = severity.name,
    status = status,
    createdAt = createdAt,
    updatedAt = updatedAt
)
