package demo.incident.dto.api

import demo.incident.model.IncidentSeverity
import demo.incident.model.IncidentStatus
import demo.incident.model.IncidentType
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import org.springframework.data.domain.Sort

data class IncidentSearchParams(
    val status: IncidentStatus? = null,
    val type: IncidentType? = null,
    val severity: IncidentSeverity? = null,
    @field:Size(min = 3, message = "query must be at least 3 characters long")
    val query: String? = null,
    @field:Min(0)
    val page: Int = 0,
    @field:Min(1)
    @field:Max(100)
    val size: Int = 10,
    @field:Pattern(
        regexp = "createdAt|updatedAt|severity|status|title",
        message = "Invalid sort field"
    )
    val sortBy: String = "createdAt",
    val direction: Sort.Direction = Sort.Direction.DESC
)