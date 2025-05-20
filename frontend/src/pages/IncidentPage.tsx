import {useEffect, useState} from 'react'
import {IncidentRequest, IncidentResponse} from '../types'
import {get, post} from '../utils/api'
import IncidentCreateRow from "../components/incident/IncidentCreateRow.tsx"
import IncidentList from "../components/incident/IncidentList.tsx"
import PaginationControls from "../components/incident/PaginationControls.tsx"
import IncidentFilterPanel from "../components/incident/IncidentFilterPanel.tsx"
import Spinner from "../components/common/Spinner.tsx"


interface IncidentPageResult {
    content?: IncidentResponse[]
    totalPages?: number
    totalElements?: number
    number?: number
}

export default function IncidentPage() {
    const [incidents, setIncidents] = useState<IncidentResponse[]>([])
    const [query, setQuery] = useState('')
    const [statusFilter, setStatusFilter] = useState('')
    const [typeFilter, setTypeFilter] = useState('')
    const [severityFilter, setSeverityFilter] = useState('')
    const [sortBy, setSortBy] = useState('createdAt')
    const [direction, setDirection] = useState<'ASC' | 'DESC'>('DESC')
    const [loading, setLoading] = useState(false)
    const [page, setPage] = useState(0)
    const [totalPages, setTotalPages] = useState(1)
    const [totalElements, setTotalElements] = useState<number | null>(null)

    const size = 5

    useEffect(() => {
        fetchIncidents()
    }, [query, sortBy, direction, page, statusFilter, typeFilter, severityFilter])

    async function fetchIncidents() {
        setLoading(true)
        try {
            const params = new URLSearchParams({
                sortBy,
                direction,
                page: String(page),
                size: String(size),
            })
            if (query.length > 0 && query.length <= 100) {
                params.append('query', query)
            }
            if (statusFilter) params.append('status', statusFilter)
            if (typeFilter) params.append('type', typeFilter)
            if (severityFilter) params.append('severity', severityFilter)

            const data = await get<IncidentPageResult | IncidentResponse[]>(`/api/incidents?${params.toString()}`)

            if (Array.isArray(data)) {
                setIncidents(data)
                setTotalPages(1)
                setTotalElements(data.length)
            } else if (Array.isArray(data.content)) {
                setIncidents(data.content)
                setTotalPages(data.totalPages ?? 1)
                setTotalElements(data.totalElements ?? null)
            }
        } finally {
            setLoading(false)
        }
    }

    async function handleCreate(incident: IncidentRequest) {
        const created = await post<IncidentResponse, IncidentRequest>('/api/incidents', incident)

        setIncidents(prev => [{ ...created, __highlight: true }, ...prev])
        setTotalElements(prev => (prev ?? 0) + 1)
        setPage(0)
    }

    return (
        <div className="min-h-screen bg-gray-100 py-10 px-4">
            <div className="max-w-5xl mx-auto bg-white rounded-2xl shadow-md p-6">
                <div className="flex items-center justify-between mb-6">
                    <div className="flex items-center gap-4">
                        <a href="/" className="flex items-center gap-4">
                            <img src="/qm.svg" alt="QM Logo" className="h-10 w-auto"/>
                            <h1 className="text-2xl font-bold text-[#00a0a7]">Incident Management</h1>
                        </a>
                    </div>
                </div>


                <IncidentFilterPanel
                    query={query}
                    statusFilter={statusFilter}
                    typeFilter={typeFilter}
                    severityFilter={severityFilter}
                    sortBy={sortBy}
                    direction={direction}
                    onChange={(key, value) => {
                        switch (key) {
                            case 'query':
                                setQuery(value);
                                break
                            case 'statusFilter':
                                setStatusFilter(value);
                                break
                            case 'typeFilter':
                                setTypeFilter(value);
                                break
                            case 'severityFilter':
                                setSeverityFilter(value);
                                break
                            case 'sortBy':
                                setSortBy(value);
                                break
                            case 'direction':
                                setDirection(value as 'ASC' | 'DESC');
                                break
                        }
                        setPage(0)
                    }}
                    onReset={() => {
                        setQuery('')
                        setStatusFilter('')
                        setTypeFilter('')
                        setSeverityFilter('')
                        setSortBy('createdAt')
                        setDirection('DESC')
                        setPage(0)
                    }}
                />

                <IncidentCreateRow onCreate={handleCreate}/>

                {loading ? (
                    <div className="flex justify-center items-center gap-2 text-gray-500 text-sm mt-6">
                        <Spinner />
                        <span>Lade Incidents…</span>
                    </div>

                ) : (
                    <>
                        {!loading && totalElements !== null && (
                            <div className="text-sm text-gray-600 mb-2">
                                <span className="font-semibold text-[#00a0a7]">{totalElements}</span> Incidents gefunden
                            </div>
                        )}
                        <IncidentList incidents={incidents} onStatusUpdated={fetchIncidents}/>
                        <PaginationControls page={page} totalPages={totalPages} onPageChange={setPage}/>
                    </>
                )}
            </div>
        </div>
    )
}
