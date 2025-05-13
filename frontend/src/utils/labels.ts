export const statusLabels: Record<string, string> = {
    OPEN: 'Offen',
    IN_PROGRESS: 'In Arbeit',
    RESOLVED: 'Erledigt'
}

export const statusColors: Record<string, string> = {
    OPEN: 'text-gray-700 bg-gray-100 border-gray-300',
    IN_PROGRESS: 'bg-purple-100 text-purple-800 border-purple-300',
    RESOLVED: 'text-green-800 bg-green-100 border-green-300',
}

export const severityLabels: Record<string, string> = {
    LOW: 'Niedrig',
    MEDIUM: 'Mittel',
    HIGH: 'Hoch'
}

export const severityColors: Record<string, string> = {
    LOW: 'bg-yellow-100 text-yellow-800 border-yellow-300',
    MEDIUM: 'text-orange-800 bg-orange-100 border-orange-300',
    HIGH: 'text-red-800 bg-red-100 border-red-300'
}

export const typeLabels: Record<string, string> = {
    NONCONFORMITY: 'Abweichung',
    CUSTOMER_COMPLAINT: 'Kundenbeschwerde',
    AUDIT_FINDING: 'Auditabweichung',
    IMPROVEMENT_SUGGESTION: 'Verbesserungsvorschlag',
    NEAR_MISS: 'Beinahevorfall'
}
