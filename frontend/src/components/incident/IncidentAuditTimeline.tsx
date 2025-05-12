import {IncidentAuditLogResponse} from '../../types'
import {statusLabels} from '../../utils/labels.ts'

interface Props {
    logs: IncidentAuditLogResponse[]
}

export default function IncidentAuditTimeline({logs}: Props) {
    return (
        <ol className="relative border-l-2 border-gray-200 pl-5 space-y-4 mt-2">
            {logs.map((log) => (
                <li key={log.id} className="relative ml-1">
                    <div
                        className="absolute -left-[13px] top-1 w-3 h-3 bg-[#00a0a7] rounded-full border-2 border-white shadow-sm"></div>
                    <div className="text-sm text-gray-700">
                        <span className="font-semibold">{statusLabels[log.oldStatus]}</span>
                        <span className="mx-1">→</span>
                        <span className="font-semibold">{statusLabels[log.newStatus]}</span>
                    </div>
                    <div className="text-xs text-gray-500">
                        {new Date(log.changedAt).toLocaleString()}
                    </div>
                </li>
            ))}
        </ol>
    )
}
