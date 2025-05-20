package demo.incident.repository

import demo.incident.model.IncidentAuditLog
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.JpaRepository

interface IncidentAuditLogRepository : JpaRepository<IncidentAuditLog, Long> {
    fun findByIncident_Id(incidentId: Long, sort: Sort): List<IncidentAuditLog>
    fun existsByIncident_Id(incidentId: Long): Boolean
}
