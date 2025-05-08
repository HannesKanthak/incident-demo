package demo.incident.repository

import org.springframework.data.jpa.repository.JpaRepository
import demo.incident.model.Incident

interface IncidentRepository : JpaRepository<Incident, Long>
