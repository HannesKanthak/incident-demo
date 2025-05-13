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
                {loading ?  (
                    <div className="flex items-center gap-2 text-gray-500 text-sm">
                        <svg
                            className="animate-spin h-4 w-4 text-[#00a0a7]"
                            xmlns="http://www.w3.org/2000/svg"
                            fill="none"
                            viewBox="0 0 24 24"
                        >
                            <circle
                                className="opacity-25"
                                cx="12"
                                cy="12"
                                r="10"
                                stroke="currentColor"
                                strokeWidth="4"
                            />
                            <path
                                className="opacity-75"
                                fill="currentColor"
                                d="M4 12a8 8 0 018-8v4a4 4 0 00-4 4H4z"
                            />
                        </svg>
                        <span>Lade Audit-Log…</span>
                    </div>
                ) : logs && logs.length > 0 ? (
                    <IncidentAuditTimeline logs={logs}/>
                ) : (
                    <p className="text-sm text-gray-500">Keine Audit-Einträge vorhanden.</p>
                )}
            </div>
        </div>
    )
}
