package qm.demo.incident.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "incident_audit_log")
data class IncidentAuditLog(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val incidentId: Long,

    val oldStatus: String,
    val newStatus: String,

    val changedAt: LocalDateTime = LocalDateTime.now()
)
