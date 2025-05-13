package demo.incident.controller

import demo.incident.dto.api.IncidentAuditLogResponse
import demo.incident.service.IncidentAuditLogService
import org.springframework.data.domain.Sort
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/incidents")
class IncidentAuditLogController(
    private val auditService: IncidentAuditLogService
) {

    @GetMapping("/{id}/audit")
    fun getAuditLog(
        @PathVariable id: Long,
        @RequestParam(defaultValue = "ASC") direction: Sort.Direction
    ): List<IncidentAuditLogResponse> {
        return auditService.getAuditLog(id, direction)
    }
}
