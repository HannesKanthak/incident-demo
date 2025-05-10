package demo.incident.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "incident_audit_log")
data class IncidentAuditLog(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "incident_id", nullable = false)
    val incident: Incident,
    val oldStatus: String,
    val newStatus: String,
    val changedAt: LocalDateTime = LocalDateTime.now()
)
