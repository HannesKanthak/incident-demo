package demo.incident.kafka

import demo.incident.dto.event.IncidentStatusChangedEvent
import demo.incident.model.Incident
import demo.incident.model.IncidentAuditLog
import demo.incident.model.IncidentSeverity
import demo.incident.model.IncidentStatus
import demo.incident.model.IncidentType
import demo.incident.repository.IncidentAuditLogRepository
import demo.incident.repository.IncidentRepository
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import java.time.LocalDateTime
import java.util.Optional

class IncidentEventConsumerTest : StringSpec({

    "should save audit log on status change event" {
        val repo = mockk<IncidentAuditLogRepository>(relaxed = true)
        val incidentRepo = mockk<IncidentRepository>()
        val incident = Incident(
            id = 42L,
            title = "Test Incident",
            description = "Test Description",
            type = IncidentType.AUDIT_FINDING,
            severity = IncidentSeverity.HIGH,
            status = IncidentStatus.OPEN,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )

        every { incidentRepo.findById(42L) } returns Optional.of(incident)

        val consumer = IncidentEventConsumer(auditRepo = repo, incidentRepo = incidentRepo)

        val event = IncidentStatusChangedEvent(
            incidentId = 42L,
            oldStatus = IncidentStatus.OPEN,
            newStatus = IncidentStatus.RESOLVED,
            changedAt = LocalDateTime.now()
        )

        val slot = slot<IncidentAuditLog>()
        every { repo.save(capture(slot)) } returns mockk()

        consumer.handleStatusChange(event)

        slot.captured.incident shouldBe incident
        slot.captured.oldStatus shouldBe "OPEN"
        slot.captured.newStatus shouldBe "RESOLVED"
        slot.captured.changedAt shouldBe event.changedAt
    }
})
