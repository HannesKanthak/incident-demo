package demo.incident.model

import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.Version
import java.time.LocalDateTime

@Entity
@Table(name = "incidents")
data class Incident(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val title: String,
    val description: String,
    @Enumerated(EnumType.STRING)
    val type: IncidentType,
    @Enumerated(EnumType.STRING)
    val severity: IncidentSeverity,
    @Enumerated(EnumType.STRING)
    var status: IncidentStatus = IncidentStatus.OPEN,
    var updatedAt: LocalDateTime,
    val createdAt: LocalDateTime,
    @Version
    val version: Long = 0L
)

enum class IncidentType {
    INCIDENT,
    IMPROVEMENT,
    COMPLIANCE
}

enum class IncidentSeverity {
    LOW,
    MEDIUM,
    HIGH
}

enum class IncidentStatus {
    OPEN,
    IN_PROGRESS,
    RESOLVED
}
