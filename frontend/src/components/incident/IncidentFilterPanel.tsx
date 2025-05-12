import { useState, useEffect, useRef } from 'react'
import { typeLabels } from '../../utils/labels'

interface Props {
    query: string
    statusFilter: string
    typeFilter: string
    severityFilter: string
    sortBy: string
    direction: 'ASC' | 'DESC'
    onChange: (key: string, value: string) => void
    onReset: () => void
}

export default function IncidentFilterPanel({
                                                query,
                                                statusFilter,
                                                typeFilter,
                                                severityFilter,
                                                sortBy,
                                                direction,
                                                onChange,
                                                onReset
                                            }: Props) {
    const [sortOpen, setSortOpen] = useState(false)
    const dropdownRef = useRef<HTMLDivElement>(null)

    useEffect(() => {
        function handleClickOutside(event: MouseEvent) {
            if (
                dropdownRef.current &&
                !dropdownRef.current.contains(event.target as Node)
            ) {
                setSortOpen(false)
            }
        }

        document.addEventListener('mousedown', handleClickOutside)
        return () => {
            document.removeEventListener('mousedown', handleClickOutside)
        }
    }, [])

    const sortOptions = [
        { value: 'createdAt', label: 'Erstellt' },
        { value: 'updatedAt', label: 'Geändert' },
        { value: 'title', label: 'Titel' }
    ]

    const selectedLabel = sortOptions.find(opt => opt.value === sortBy)?.label || 'Sortierung'

    const toggleDirection = () => {
        const newDir = direction === 'ASC' ? 'DESC' : 'ASC'
        onChange('direction', newDir)
    }

    const handleSortFieldChange = (value: string) => {
        onChange('sortBy', value)
        setSortOpen(false)
    }

    const isFiltered = !!(query || statusFilter || typeFilter || severityFilter)

    return (
        <div className="mb-6 space-y-4 relative">

            {/* Filterzeile */}
            <div className="bg-gray-50 border border-gray-200 p-4 rounded-xl shadow-sm">
                <div className="flex flex-col lg:flex-row lg:items-end gap-4 flex-wrap">

                    {/* Suchfeld */}
                    <div className="flex flex-col flex-grow">
                        <label className="text-sm text-gray-700 mb-1">Suchbegriff</label>
                        <input
                            type="text"
                            placeholder="mind. 3 Zeichen"
                            className="border border-gray-300 rounded-md px-2 py-1.5 text-sm focus:outline-none focus:ring-2 focus:ring-[#00a0a7]"
                            value={query}
                            onChange={e => onChange('query', e.target.value)}
                        />
                    </div>

                    {/* Status */}
                    <div className="flex flex-col w-full sm:w-auto">
                        <label className="text-sm text-gray-700 mb-1">Status</label>
                        <select
                            className="border border-gray-300 rounded-md px-2 py-1.5 text-sm"
                            value={statusFilter}
                            onChange={e => onChange('statusFilter', e.target.value)}
                        >
                            <option value="">Alle</option>
                            <option value="OPEN">Offen</option>
                            <option value="IN_PROGRESS">In Arbeit</option>
                            <option value="RESOLVED">Erledigt</option>
                        </select>
                    </div>

                    {/* Typ */}
                    <div className="flex flex-col w-full sm:w-auto">
                        <label className="text-sm text-gray-700 mb-1">Typ</label>
                        <select
                            className="border border-gray-300 rounded-md px-2 py-1.5 text-sm"
                            value={typeFilter}
                            onChange={e => onChange('typeFilter', e.target.value)}
                        >
                            <option value="">Alle</option>
                            <option value="INCIDENT">{typeLabels.INCIDENT}</option>
                            <option value="IMPROVEMENT">{typeLabels.IMPROVEMENT}</option>
                            <option value="COMPLIANCE">{typeLabels.COMPLIANCE}</option>
                        </select>
                    </div>

                    {/* Schweregrad */}
                    <div className="flex flex-col w-full sm:w-auto">
                        <label className="text-sm text-gray-700 mb-1">Schweregrad</label>
                        <select
                            className="border border-gray-300 rounded-md px-2 py-1.5 text-sm"
                            value={severityFilter}
                            onChange={e => onChange('severityFilter', e.target.value)}
                        >
                            <option value="">Alle</option>
                            <option value="LOW">Niedrig</option>
                            <option value="MEDIUM">Mittel</option>
                            <option value="HIGH">Hoch</option>
                        </select>
                    </div>

                    {/* Zurücksetzen */}
                    {isFiltered && (
                        <div className="ml-auto pt-1">
                            <button
                                onClick={onReset}
                                className="text-[#00a0a7] hover:text-[#007b7f] text-xl"
                                title="Filter zurücksetzen"
                            >
                                ⟲
                            </button>
                        </div>
                    )}
                </div>
            </div>

            {/* Sortierung */}
            <div className="flex justify-end">
                <div className="relative inline-block" ref={dropdownRef}>
                    <button
                        onClick={() => setSortOpen(prev => !prev)}
                        className="flex items-center gap-2 border border-gray-300 rounded-md px-3 py-1.5 text-sm bg-white hover:bg-gray-50 transition"
                    >
                        <span className="text-gray-700">Sortierung:</span>
                        <span className="font-medium text-gray-900">{selectedLabel}</span>
                        <span className="text-[#00a0a7] text-base">{direction === 'ASC' ? '↑' : '↓'}</span>
                    </button>

                    {sortOpen && (
                        <div className="absolute right-0 mt-1 w-40 bg-white border border-gray-200 rounded-md shadow-lg z-10">
                            {sortOptions.map(opt => (
                                <button
                                    key={opt.value}
                                    onClick={() => handleSortFieldChange(opt.value)}
                                    className={`block w-full text-left px-4 py-2 text-sm hover:bg-gray-100 ${
                                        sortBy === opt.value ? 'bg-gray-100 font-medium' : ''
                                    }`}
                                >
                                    {opt.label}
                                </button>
                            ))}
                            <button
                                onClick={toggleDirection}
                                className="block w-full text-left px-4 py-2 text-sm text-[#00a0a7] hover:bg-gray-100"
                            >
                                Richtung ändern {direction === 'ASC' ? '↑' : '↓'}
                            </button>
                        </div>
                    )}
                </div>
            </div>
        </div>
    )
}
