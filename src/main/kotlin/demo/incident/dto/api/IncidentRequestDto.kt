package qm.demo.incident.dto.api

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import qm.demo.incident.model.IncidentSeverity
import qm.demo.incident.model.IncidentType

data class IncidentRequestDto(
    @field:NotBlank val title: String,
    @field:NotBlank val description: String,
    @field:NotNull val type: IncidentType,
    @field:NotNull val severity: IncidentSeverity
)
