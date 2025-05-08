package demo.incident.controller

import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*
import demo.incident.dto.api.IncidentRequestDto
import demo.incident.dto.api.IncidentResponseDto
import demo.incident.dto.api.IncidentStatusUpdateDto
import demo.incident.service.IncidentService

@RestController
@RequestMapping("/api/incidents")
class IncidentController(
    private val service: IncidentService,

    ) {

    @PostMapping
    fun create(@Valid @RequestBody body: IncidentRequestDto) = service.createIncident(body)

    @GetMapping
    fun list(): List<IncidentResponseDto> = service.listIncidents()

    @PatchMapping("/{id}/status")
    fun updateStatus(
        @PathVariable id: Long,
        @Valid @RequestBody body: IncidentStatusUpdateDto
    ): IncidentResponseDto = service.updateStatus(id, body)
}
