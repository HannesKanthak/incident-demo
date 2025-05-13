import {useEffect, useState} from 'react'
import {IncidentResponse, IncidentStatus} from '../../types'
import {patch} from '../../utils/api'
import IncidentAuditLogAccordion from './IncidentAuditLogAccordion'
import {severityLabels, statusColors, statusLabels, typeLabels} from '../../utils/labels'
import {formatRelativeTime} from '../../utils/date'

interface Props {
    incident: IncidentResponse & { __highlight?: boolean }
    onStatusUpdated: () => void
}

export default function IncidentRow({incident, onStatusUpdated}: Props) {
    const [editStatus, setEditStatus] = useState<IncidentStatus | null>(null)
    const [isExpanded, setIsExpanded] = useState(false)
    const [highlight, setHighlight] = useState<boolean>(Boolean(incident.__highlight))

    useEffect(() => {
        if (highlight) {
            const timeout = setTimeout(() => setHighlight(false), 1200)
            return () => clearTimeout(timeout)
        }
    }, [highlight])

    const currentStatus = editStatus ?? incident.status
    const isDirty = editStatus !== null && editStatus !== incident.status
    const isResolved = incident.status === 'RESOLVED'

    async function handleSave() {
        if (!editStatus || editStatus === incident.status) return
        await patch(`/api/incidents/${incident.id}/status`, {status: editStatus})
        setEditStatus(null)
        onStatusUpdated()
    }

    return (
        <div
            className={`border border-gray-200 rounded-xl mb-3 shadow-sm transition hover:border-[#00a0a7] ${highlight ? 'highlight-flash' : ''}`}
        >
            <div className="px-4 py-3 flex flex-col gap-2 text-sm text-gray-700">

                {/* Kopfzeile: Typ + Schweregrad + Titel */}
                <div className="flex flex-wrap justify-between items-start gap-2">
                    <div className="flex flex-wrap items-center gap-2">
                        <span className="px-2 py-0.5 rounded text-xs border text-blue-800 bg-blue-100 border-blue-300">
                            {typeLabels[incident.type]}
                        </span>
                        <span className={`px-2 py-0.5 rounded text-xs border ${incident.severity === 'LOW'
                            ? 'text-yellow-800 bg-yellow-100 border-yellow-300'
                            : incident.severity === 'MEDIUM'
                                ? 'text-orange-800 bg-orange-100 border-orange-300'
                                : 'text-red-800 bg-red-100 border-red-300'
                        }`}>
                            {severityLabels[incident.severity]}
                        </span>
                        <strong className="text-base ml-2">{incident.title}</strong>
                    </div>

                    {/* Status-Dropdown */}
                    <div className="flex items-center gap-2">
                        <select
                            disabled={isResolved}
                            className={`border text-xs rounded-md px-2 py-0.5 cursor-pointer focus:outline-none focus:ring-1 focus:ring-[#00a0a7] disabled:opacity-50 disabled:cursor-not-allowed ${statusColors[currentStatus]}`}
                            value={currentStatus}
                            onChange={e => setEditStatus(e.target.value as IncidentStatus)}
                        >
                            <option value="OPEN">{statusLabels.OPEN}</option>
                            <option value="IN_PROGRESS">{statusLabels.IN_PROGRESS}</option>
                            <option value="RESOLVED">{statusLabels.RESOLVED}</option>
                        </select>

                        {!isResolved && isDirty && (
                            <>
                                <button
                                    onClick={handleSave}
                                    title="Status speichern"
                                    className="text-green-600 hover:text-green-800 text-xs px-1 transition-transform transform hover:scale-110 cursor-pointer"
                                >
                                    ✔
                                </button>
                                <button
                                    onClick={() => setEditStatus(null)}
                                    title="Änderung verwerfen"
                                    className="text-red-500 hover:text-red-700 text-xs px-1 transition-transform transform hover:scale-110 cursor-pointer"
                                >
                                    ✖
                                </button>
                            </>
                        )}
                    </div>
                </div>

                {/* Zeitangaben + Ausklapp-Pfeil */}
                <div className="text-xs text-gray-500 mt-1 flex flex-wrap justify-between items-center gap-2">
                    <div className="flex gap-6">
                        <span title={new Date(incident.createdAt).toLocaleString()}>
                            🕒 erstellt: {formatRelativeTime(incident.createdAt)}
                        </span>
                        <span title={new Date(incident.updatedAt).toLocaleString()}>
                            🔄 geändert: {formatRelativeTime(incident.updatedAt)}
                        </span>
                    </div>
                    <button
                        onClick={() => setIsExpanded(prev => !prev)}
                        className="flex items-center gap-1 text-gray-400 hover:text-[#00a0a7] text-sm cursor-pointer"
                        title={isExpanded ? "Details verbergen" : "Details anzeigen"}
                        aria-label="Details anzeigen/verbergen"
                    >
                        <span>Details</span>
                        <span
                            className={`transition-transform duration-200 ${
                                isExpanded ? 'rotate-180' : ''
                            } w-3 h-3`}
                        >
                            <svg
                                viewBox="0 0 20 20"
                                fill="currentColor"
                                className="w-full h-full"
                                xmlns="http://www.w3.org/2000/svg"
                            >
                                <path
                                    fillRule="evenodd"
                                    d="M5.23 7.79a.75.75 0 011.06-.02L10 11.44l3.71-3.67a.75.75 0 111.04 1.08l-4.25 4.2a.75.75 0 01-1.04 0L5.21 8.85a.75.75 0 01.02-1.06z"
                                    clipRule="evenodd"
                                />
                            </svg>
                        </span>
                    </button>

                </div>

                {/* Detailbereich */}
                {isExpanded && (
                    <IncidentAuditLogAccordion incidentId={incident.id} description={incident.description}/>
                )}
            </div>
        </div>
    )
}
