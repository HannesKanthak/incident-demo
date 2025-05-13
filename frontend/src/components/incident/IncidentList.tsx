import { IncidentResponse } from '../../types'
import IncidentRow from './IncidentRow'

interface Props {
    incidents: IncidentResponse[]
    onStatusUpdated: () => void
}

export default function IncidentList({ incidents, onStatusUpdated }: Props) {
    return (
        <div>
            {incidents.map((incident) => (
                <IncidentRow
                    key={incident.id}
                    incident={incident}
                    onStatusUpdated={onStatusUpdated}
                />
            ))}
        </div>
    )
}