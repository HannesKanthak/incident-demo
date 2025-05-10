package demo.incident.controller

import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*
import demo.incident.dto.api.IncidentRequest
import demo.incident.dto.api.IncidentResponse
import demo.incident.dto.api.IncidentStatusUpdate
import demo.incident.service.IncidentService

@RestController
@RequestMapping("/api/incidents")
class IncidentController(
    private val service: IncidentService,

    ) {

    @PostMapping
    fun create(@Valid @RequestBody body: IncidentRequest) = service.createIncident(body)

    @GetMapping
    fun list(): List<IncidentResponse> = service.listIncidents()

    @PatchMapping("/{id}/status")
    fun updateStatus(
        @PathVariable id: Long,
        @Valid @RequestBody body: IncidentStatusUpdate
    ): IncidentResponse = service.updateStatus(id, body)
}
