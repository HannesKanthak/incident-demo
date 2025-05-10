package demo.incident.dto.api

import demo.incident.model.Incident
import demo.incident.model.IncidentStatus
import java.time.LocalDateTime

data class IncidentResponse(
    val id: Long,
    val title: String,
    val description: String,
    val type: String,
    val severity: String,
    val status: IncidentStatus,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime?
)

fun Incident.toResponseDto() = IncidentResponse(
    id = id,
    title = title,
    description = description,
    type = type.name,
    severity = severity.name,
    status = status,
    createdAt = createdAt,
    updatedAt = updatedAt
)
