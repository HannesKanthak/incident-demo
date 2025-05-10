ALTER TABLE incident_audit_log
    ADD CONSTRAINT fk_incident
        FOREIGN KEY (incident_id)
            REFERENCES incidents(id)
            ON DELETE CASCADE;