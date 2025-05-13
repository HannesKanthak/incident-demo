package demo.incident.repository


import demo.incident.model.Incident
import demo.incident.model.IncidentSeverity
import demo.incident.model.IncidentStatus
import demo.incident.model.IncidentType
import jakarta.persistence.criteria.Predicate
import org.springframework.data.jpa.domain.Specification

object IncidentSpecifications {
    fun withFilters(
        status: IncidentStatus?,
        type: IncidentType?,
        severity: IncidentSeverity?,
        query: String?
    ): Specification<Incident> = Specification { root, _, cb ->
        val predicates = mutableListOf<Predicate>()

        status?.let {
            predicates.add(cb.equal(root.get<IncidentStatus>("status"), it))
        }
        type?.let {
            predicates.add(cb.equal(root.get<IncidentType>("type"), it))
        }
        severity?.let {
            predicates.add(cb.equal(root.get<IncidentSeverity>("severity"), it))
        }
        query?.let {
            val pattern = query.trim()
                .lowercase()
                .replace("\\", "\\\\")
                .replace("%", "\\%")
                .replace("_", "\\_")
                .let { escaped -> "%$escaped%" }

            predicates.add(
                cb.or(
                    cb.like(cb.lower(root.get("title")), pattern),
                    cb.like(cb.lower(root.get("description")), pattern)
                )
            )
        }

        cb.and(*predicates.toTypedArray())
    }
}
