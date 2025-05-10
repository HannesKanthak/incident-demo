package demo.incident.dto.api

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import demo.incident.model.IncidentSeverity
import demo.incident.model.IncidentType

data class IncidentRequest(
    @field:NotBlank val title: String,
    @field:NotBlank val description: String,
    @field:NotNull val type: IncidentType,
    @field:NotNull val severity: IncidentSeverity
)
