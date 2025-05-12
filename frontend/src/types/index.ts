export type IncidentStatus = 'OPEN' | 'IN_PROGRESS' | 'RESOLVED'

export interface IncidentResponse {
    id: number
    title: string
    description: string
    type: string
    severity: string
    status: IncidentStatus
    createdAt: string
    updatedAt: string
}

export interface IncidentRequest {
    title: string
    description: string
    type: string
    severity: string
}

export interface IncidentAuditLogResponse {
    id: number
    oldStatus: string
    newStatus: string
    changedAt: string
}