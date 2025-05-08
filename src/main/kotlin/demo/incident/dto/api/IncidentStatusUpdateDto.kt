package demo.incident.dto.api

import jakarta.validation.constraints.NotNull
import demo.incident.model.IncidentStatus

data class IncidentStatusUpdateDto(
    @field:NotNull val status: IncidentStatus
)
