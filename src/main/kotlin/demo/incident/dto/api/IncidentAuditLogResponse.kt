package demo.incident.dto.api

import demo.incident.model.IncidentAuditLog
import java.time.LocalDateTime

data class IncidentAuditLogResponse(
    val id: Long,
    val oldStatus: String,
    val newStatus: String,
    val changedAt: LocalDateTime
)

fun IncidentAuditLog.toResponseDto() = IncidentAuditLogResponse(
    id = id,
    oldStatus = oldStatus,
    newStatus = newStatus,
    changedAt = changedAt
)