package demo.incident.repository

import org.springframework.data.jpa.repository.JpaRepository
import demo.incident.model.Incident
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface IncidentRepository : JpaRepository<Incident, Long>, JpaSpecificationExecutor<Incident>
