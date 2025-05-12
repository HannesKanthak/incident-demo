package demo.incident.controller

import demo.incident.dto.api.IncidentRequest
import demo.incident.dto.api.IncidentResponse
import demo.incident.dto.api.IncidentSearchParams
import demo.incident.dto.api.IncidentStatusUpdate
import demo.incident.service.IncidentService
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/incidents")
class IncidentController(private val service: IncidentService) {

    @PostMapping
    fun create(@Valid @RequestBody body: IncidentRequest) = service.createIncident(body)

    @GetMapping
    fun search(@Validated @ModelAttribute params: IncidentSearchParams): Page<IncidentResponse> =
        service.search(params)

    @PatchMapping("/{id}/status")
    fun updateStatus(
        @PathVariable id: Long,
        @Valid @RequestBody body: IncidentStatusUpdate
    ): IncidentResponse = service.updateStatus(id, body)
}
