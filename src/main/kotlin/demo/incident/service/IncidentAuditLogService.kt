package demo.incident.service

import demo.incident.dto.api.IncidentAuditLogResponse
import demo.incident.dto.api.toResponseDto
import demo.incident.repository.IncidentAuditLogRepository
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class IncidentAuditLogService(
    private val auditRepo: IncidentAuditLogRepository
) {
    fun getAuditLog(incidentId: Long, direction: Sort.Direction): List<IncidentAuditLogResponse> {
        if (!auditRepo.existsByIncidentId(incidentId)) {
            throw IllegalArgumentException("Incident $incidentId not found")
        }
        val sort = Sort.by(direction, "changedAt")
        return auditRepo.findByIncidentId(incidentId, sort).map { it.toResponseDto() }
    }
}
