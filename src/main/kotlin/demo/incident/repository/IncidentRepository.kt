package qm.demo.incident.repository

import org.springframework.data.jpa.repository.JpaRepository
import qm.demo.incident.model.Incident

interface IncidentRepository : JpaRepository<Incident, Long>
