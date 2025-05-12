package demo.incident.repository

import demo.incident.model.IncidentAuditLog
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.JpaRepository

interface IncidentAuditLogRepository : JpaRepository<IncidentAuditLog, Long> {
    fun findByIncidentId(incidentId: Long, sort: Sort): List<IncidentAuditLog>
    fun existsByIncidentId(incidentId: Long): Boolean
}
