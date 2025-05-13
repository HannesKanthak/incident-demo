package demo

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import demo.incident.model.Incident
import demo.incident.model.IncidentAuditLog
import demo.incident.model.IncidentSeverity
import demo.incident.model.IncidentStatus
import demo.incident.model.IncidentType
import demo.incident.repository.IncidentAuditLogRepository
import demo.incident.repository.IncidentRepository
import jakarta.annotation.PostConstruct
import org.springframework.context.annotation.Profile
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import kotlin.random.Random

@Component
@Profile("dev")
class JsonDataSeeder(
    private val incidentRepo: IncidentRepository,
    private val auditRepo: IncidentAuditLogRepository
) {
    private val objectMapper = jacksonObjectMapper()

    data class Template(
        val title: String,
        val description: String,
        val type: IncidentType,
        val severity: IncidentSeverity
    )

    @PostConstruct
    fun seedFromJson() {
        if (incidentRepo.count() > 0) return

        val inputStream = ClassPathResource("incident_templates.json").inputStream
        val templates = objectMapper.readValue<List<Template>>(inputStream)

        val now = LocalDateTime.now()

        val incidents = templates.shuffled()
            .map {
                val createdAt = now
                    .minusDays(Random.Default.nextLong(3, 90))
                    .minusHours(Random.Default.nextLong(0, 24))
                    .minusMinutes(Random.Default.nextLong(0, 60))
                val statusPath = generateStatusPath()

                Incident(
                    title = it.title,
                    description = it.description,
                    type = IncidentType.entries.toTypedArray().random(),
                    severity = IncidentSeverity.entries.toTypedArray().random(),
                    status = statusPath.last(),
                    createdAt = createdAt,
                    updatedAt = createdAt
                        .plusDays(statusPath.size.toLong() - 1)
                        .plusHours(Random.Default.nextLong(0, 24))
                        .plusMinutes(Random.Default.nextLong(0, 60))
                ) to statusPath
            }

        val incidentPairs = incidents.map { it.first to it.second }
        val savedIncidents = incidentRepo.saveAll(incidentPairs.map { it.first })

        val auditLogs = savedIncidents.zip(incidentPairs.map { it.second }).flatMap { (incident, statusPath) ->
            val baseTime = incident.createdAt

            buildList {
                add(
                    IncidentAuditLog(
                        incident = incident,
                        oldStatus = "-",
                        newStatus = "OPEN",
                        changedAt = baseTime
                    )
                )

                (1..statusPath.size - 1).forEach { index ->
                    add(
                        IncidentAuditLog(
                            incident = incident,
                            oldStatus = statusPath[index - 1].name,
                            newStatus = statusPath[index].name,
                            changedAt = baseTime.plusDays(index.toLong())
                                .plusHours(Random.Default.nextLong(0, 24))
                                .plusMinutes(Random.Default.nextLong(0, 60))
                        )
                    )
                }
            }
        }

        auditRepo.saveAll(auditLogs)
    }

    private fun generateStatusPath(): List<IncidentStatus> {
        return when (Random.Default.nextInt(3)) {
            0 -> listOf(IncidentStatus.OPEN)
            1 -> listOf(IncidentStatus.OPEN, IncidentStatus.IN_PROGRESS)
            else -> listOf(IncidentStatus.OPEN, IncidentStatus.IN_PROGRESS, IncidentStatus.RESOLVED)
        }
    }
}
