package demo.incident.dto.api

import jakarta.validation.constraints.NotNull
import demo.incident.model.IncidentStatus

data class IncidentStatusUpdate(
    @field:NotNull val status: IncidentStatus
)
