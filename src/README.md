# Incident-Demo API Doku

## Endpoints

### POST /api/incidents
- **Beschreibung**: Erstellt ein neues Incident.
- **Request Body**: `IncidentRequest`
- **Response**: `IncidentResponse`
- **Side Effects**: Kafka-Event `incident.created` wird gesendet.

### GET /api/incidents
- **Beschreibung**: Listet Incidents gefiltert und paginiert.
- **Query-Parameter**: `IncidentSearchParams`
- **Response**: Paginierte Liste von `IncidentResponse`

### PATCH /api/incidents/{id}/status
- **Beschreibung**: Aktualisiert den Status eines Incidents.
- **Request Body**: `IncidentStatusUpdate`
- **Response**: `IncidentResponse`
- **Side Effects**: Kafka-Event `incident.updated` wird gesendet.

### GET /api/incidents/{id}/audit
- **Beschreibung**: Gibt die Statusänderungshistorie eines Incidents zurück.
- **Response**: Liste von `IncidentAuditLogResponse`

## Datenmodelle

### IncidentRequest
```json
{
  "title": "Server-Ausfall",
  "description": "Datenbank war nicht erreichbar",
  "type": "INCIDENT",
  "severity": "HIGH"
}
```

### IncidentResponse
```json
{
  "id": 1,
  "title": "Server-Ausfall",
  "description": "Datenbank war nicht erreichbar",
  "type": "INCIDENT",
  "severity": "HIGH",
  "status": "OPEN",
  "createdAt": "2024-05-01T10:15:30",
  "updatedAt": "2024-05-01T10:15:30"
}
```

### IncidentStatusUpdate
```json
{
  "status": "RESOLVED"
}
```

### IncidentAuditLogResponse
```json
{
  "id": 42,
  "oldStatus": "OPEN",
  "newStatus": "IN_PROGRESS",
  "changedAt": "2024-05-01T12:00:00"
}
```

## Kafka-Events

### incident.created
```json
{
  "id": 1,
  "title": "Server-Ausfall",
  "type": "INCIDENT",
  "severity": "HIGH",
  "createdAt": "2024-05-01T10:15:30"
}
```

### incident.updated
```json
{
  "incidentId": 1,
  "oldStatus": "OPEN",
  "newStatus": "IN_PROGRESS",
  "changedAt": "2024-05-01T12:00:00"
}
```
