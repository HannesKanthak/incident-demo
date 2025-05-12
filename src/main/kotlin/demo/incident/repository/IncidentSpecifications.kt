package demo.incident.repository


import demo.incident.model.Incident
import demo.incident.model.IncidentStatus
import org.springframework.data.jpa.domain.Specification
import jakarta.persistence.criteria.Predicate

object IncidentSpecifications {
    fun withFilters(
        status: IncidentStatus?,
        type: demo.incident.model.IncidentType?,
        severity: demo.incident.model.IncidentSeverity?,
        query: String?
    ): Specification<Incident> {
        return Specification { root, _, cb ->
            val predicates = mutableListOf<Predicate>()

            status?.let {
                predicates.add(cb.equal(root.get<IncidentStatus>("status"), it))
            }
            type?.let {
                predicates.add(cb.equal(root.get<demo.incident.model.IncidentType>("type"), it))
            }
            severity?.let {
                predicates.add(cb.equal(root.get<demo.incident.model.IncidentSeverity>("severity"), it))
            }
            query?.let {
                val likePattern = "%${it.lowercase()}%"
                predicates.add(
                    cb.or(
                        cb.like(cb.lower(root.get("title")), likePattern),
                        cb.like(cb.lower(root.get("description")), likePattern)
                    )
                )
            }

            cb.and(*predicates.toTypedArray())
        }
    }
}