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
    const isFiltered = !!(query || statusFilter || typeFilter || severityFilter)
    const combinedSort = `${sortBy}:${direction}`

    function handleSortChange(value: string) {
        const [field, dir] = value.split(':')
        onChange('sortBy', field)
        onChange('direction', dir)
    }

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
                            placeholder='z.B. "Audit", "Bananenbrot", "Retro"'
                            className="border border-gray-300 rounded-md px-2 py-1.5 text-sm focus:outline-none focus:ring-2 focus:ring-[#00a0a7]"
                            value={query}
                            onChange={e => onChange('query', e.target.value)}
                            maxLength={100}
                        />
                    </div>

                    {/* Status */}
                    <div className="flex flex-col w-full sm:w-auto">
                        <label className="text-sm text-gray-700 mb-1">Status</label>
                        <select
                            className="border border-gray-300 rounded-md px-2 py-1.5 text-sm cursor-pointer"
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
                            className="border border-gray-300 rounded-md px-2 py-1.5 text-sm cursor-pointer"
                            value={typeFilter}
                            onChange={e => onChange('typeFilter', e.target.value)}
                        >
                            <option value="">Alle</option>
                            <option value="NONCONFORMITY">{typeLabels.NONCONFORMITY}</option>
                            <option value="CUSTOMER_COMPLAINT">{typeLabels.CUSTOMER_COMPLAINT}</option>
                            <option value="AUDIT_FINDING">{typeLabels.AUDIT_FINDING}</option>
                            <option value="IMPROVEMENT_SUGGESTION">{typeLabels.IMPROVEMENT_SUGGESTION}</option>
                            <option value="NEAR_MISS">{typeLabels.NEAR_MISS}</option>
                        </select>
                    </div>

                    {/* Schweregrad */}
                    <div className="flex flex-col w-full sm:w-auto">
                        <label className="text-sm text-gray-700 mb-1">Schweregrad</label>
                        <select
                            className="border border-gray-300 rounded-md px-2 py-1.5 text-sm cursor-pointer"
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
                                className="text-[#00a0a7] hover:text-[#007b7f] text-xl cursor-pointer"
                                title="Filter zurücksetzen"
                            >
                                ⟲
                            </button>
                        </div>
                    )}
                </div>
            </div>

            {/* Sortierung */}
            <div className="flex justify-end items-center gap-2 text-sm text-gray-700">
                <label htmlFor="sortCombo">Sortierung:</label>
                <select
                    id="sortCombo"
                    value={combinedSort}
                    onChange={e => handleSortChange(e.target.value)}
                    className="border border-gray-300 rounded-md px-2 py-1.5 text-sm cursor-pointer"
                >
                    <option value="updatedAt:DESC">Zuletzt geändert</option>
                    <option value="createdAt:DESC">Zuletzt erstellt</option>
                    <option value="title:ASC">Titel A–Z</option>
                    <option value="title:DESC">Titel Z–A</option>
                </select>
            </div>
        </div>
    )
}
