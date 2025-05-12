import {useEffect, useState} from "react"
import {IncidentAuditLogResponse} from "../../types"
import {get} from "../../utils/api"
import IncidentAuditTimeline from "./IncidentAuditTimeline"

interface Props {
    incidentId: number
    description?: string
}

export default function IncidentAuditLogAccordion({incidentId, description}: Props) {
    const [logs, setLogs] = useState<IncidentAuditLogResponse[] | null>(null)
    const [loading, setLoading] = useState(false)

    useEffect(() => {
        async function fetchLogs() {
            setLoading(true)
            const data = await get<IncidentAuditLogResponse[]>(`/api/incidents/${incidentId}/audit`)
            setLogs(data)
            setLoading(false)
        }

        fetchLogs()
    }, [incidentId])

    return (
        <div className="border-t border-gray-200 text-sm text-gray-700 space-y-4 px-4 py-3">
            {description && (
                <div>
                    <strong className="block text-gray-600 mb-1">📝 Beschreibung:</strong>
                    <p className="whitespace-pre-wrap">{description}</p>
                </div>
            )}

            <div>
                <strong className="block text-gray-600 mb-1">📋 Audit-Log:</strong>
                {loading ? (
                    <p className="text-sm text-gray-500">Lade Audit-Log…</p>
                ) : logs && logs.length > 0 ? (
                    <IncidentAuditTimeline logs={logs}/>
                ) : (
                    <p className="text-sm text-gray-500">Keine Audit-Einträge vorhanden.</p>
                )}
            </div>
        </div>
    )
}
