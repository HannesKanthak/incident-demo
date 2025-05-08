package demo.incident.repository

import org.springframework.data.jpa.repository.JpaRepository
import demo.incident.model.IncidentAuditLog

interface IncidentAuditLogRepository : JpaRepository<IncidentAuditLog, Long>
