import {IncidentResponse} from '../../types'
import IncidentAuditLogAccordion from './IncidentAuditLogAccordion'

interface Props {
    incident: IncidentResponse
}

export default function IncidentDetails({incident}: Props) {
    return (
        <div className="bg-gray-50 border-t border-gray-200 px-4 py-4 text-sm text-gray-700 rounded-b-xl space-y-4">
            {incident.description && (
                <div>
                    <strong className="block text-gray-600 mb-1">Beschreibung:</strong>
                    <p className="whitespace-pre-wrap">{incident.description}</p>
                </div>
            )}
            <div>
                <strong className="block text-gray-600 mb-1">Audit-Log:</strong>
                <IncidentAuditLogAccordion incidentId={incident.id}/>
            </div>
        </div>
    )
}
