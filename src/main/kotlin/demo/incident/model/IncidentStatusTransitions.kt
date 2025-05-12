package demo.incident.model

object IncidentStatusTransitions {
    private val allowedTransitions = mapOf(
        IncidentStatus.OPEN to setOf(IncidentStatus.IN_PROGRESS),
        IncidentStatus.IN_PROGRESS to setOf(IncidentStatus.RESOLVED)
    )

    fun isValidTransition(from: IncidentStatus, to: IncidentStatus): Boolean {
        return allowedTransitions[from]?.contains(to) ?: false
    }
}