import {useState} from 'react'
import {IncidentRequest} from '../../types'
import {severityColors, severityLabels, typeLabels} from '../../utils/labels'

interface Props {
    onCreate: (incident: IncidentRequest) => void
}

export default function IncidentCreateRow({onCreate}: Props) {
    const [isVisible, setIsVisible] = useState(false)
    const [isClosing, setIsClosing] = useState(false)
    const [form, setForm] = useState<IncidentRequest>({
        title: '',
        description: '',
        type: 'NONCONFORMITY',
        severity: 'MEDIUM'
    })
    const [error, setError] = useState<string | null>(null)

    function handleChange<K extends keyof IncidentRequest>(key: K, value: IncidentRequest[K]) {
        setForm(prev => ({...prev, [key]: value}))
    }

    function handleSubmit() {
        if (!form.title || !form.description) {
            setError('Titel und Beschreibung sind erforderlich.')
            return
        }
        setIsClosing(true)
        setTimeout(() => {
            onCreate(form)
            setForm({title: '', description: '', type: 'NONCONFORMITY', severity: 'MEDIUM'})
            setIsVisible(false)
            setError(null)
            setIsClosing(false)
        }, 250)
    }

    function handleCancel() {
        setIsClosing(true)
        setTimeout(() => {
            setIsVisible(false)
            setError(null)
            setIsClosing(false)
        }, 250)
    }

    if (!isVisible) {
        return (
            <div className="mb-4">
                <button
                    onClick={() => setIsVisible(true)}
                    className="text-sm text-[#00a0a7] border border-[#00a0a7] px-3 py-1 rounded-md hover:bg-[#00a0a710] transition cursor-pointer"
                >
                    + Neuer Eintrag
                </button>
            </div>
        )
    }

    return (
        <div
            className={`${isClosing ? 'animate-fade-collapse' : 'animate-fade-expand'} border border-gray-200 rounded-xl mb-4 shadow-sm p-4 space-y-4`}>

            <input
                className={`w-full border px-3 py-1.5 text-sm rounded-md focus:outline-none focus:ring-2 focus:ring-[#00a0a7] font-semibold ${
                    !form.title && error ? 'border-red-500' : 'border-gray-300'
                }`}
                placeholder="Titel"
                value={form.title}
                onChange={e => handleChange('title', e.target.value)}
            />

            <div className="flex flex-col sm:flex-row gap-4">
                <div className="flex items-center gap-2 text-sm">
                    <span className="text-gray-600">Typ:</span>
                    <select
                        className="rounded px-2 py-0.5 border text-sm cursor-pointer border-blue-300 bg-blue-100 text-blue-800"
                        value={form.type}
                        onChange={e => handleChange('type', e.target.value as any)}
                    >
                        <option value="NONCONFORMITY">{typeLabels.NONCONFORMITY}</option>
                        <option value="CUSTOMER_COMPLAINT">{typeLabels.CUSTOMER_COMPLAINT}</option>
                        <option value="AUDIT_FINDING">{typeLabels.AUDIT_FINDING}</option>
                        <option value="IMPROVEMENT_SUGGESTION">{typeLabels.IMPROVEMENT_SUGGESTION}</option>
                        <option value="NEAR_MISS">{typeLabels.NEAR_MISS}</option>
                    </select>
                </div>

                <div className="flex items-center gap-2 text-sm">
                    <span className="text-gray-600">Schweregrad:</span>
                    <select
                        className={`rounded px-2 py-0.5 border text-sm cursor-pointer ${severityColors[form.severity]}`}
                        value={form.severity}
                        onChange={e => handleChange('severity', e.target.value as any)}
                    >
                        <option value="LOW">{severityLabels.LOW}</option>
                        <option value="MEDIUM">{severityLabels.MEDIUM}</option>
                        <option value="HIGH">{severityLabels.HIGH}</option>
                    </select>
                </div>
            </div>

            <textarea
                className={`w-full border px-3 py-1.5 text-sm rounded-md focus:outline-none focus:ring-2 focus:ring-[#00a0a7] ${
                    !form.description && error ? 'border-red-500' : 'border-gray-300'
                }`}
                placeholder="Beschreibung"
                value={form.description}
                onChange={e => handleChange('description', e.target.value)}
            />

            {error && <p className="text-sm text-red-600">{error}</p>}

            <div className="flex gap-2 justify-end pt-1">
                <button
                    onClick={handleSubmit}
                    title="Speichern"
                    className="text-green-600 hover:text-green-800 text-sm px-1 transition-transform transform hover:scale-110 cursor-pointer"
                >
                    ✔
                </button>
                <button
                    onClick={handleCancel}
                    title="Abbrechen"
                    className="text-red-500 hover:text-red-700 text-sm px-1 transition-transform transform hover:scale-110 cursor-pointer"
                >
                    ✖
                </button>
            </div>
        </div>
    )
}
