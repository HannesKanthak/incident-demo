CREATE TABLE incident_audit_log (
    id SERIAL PRIMARY KEY,
    incident_id BIGINT NOT NULL,
    old_status VARCHAR(16),
    new_status VARCHAR(16),
    changed_at TIMESTAMP NOT NULL DEFAULT now()
);