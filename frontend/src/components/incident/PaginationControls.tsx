interface Props {
    page: number
    totalPages: number
    onPageChange: (newPage: number) => void
}

export default function PaginationControls({ page, totalPages, onPageChange }: Props) {
    function goToPage(newPage: number) {
        if (newPage >= 0 && newPage < totalPages) {
            onPageChange(newPage)
        }
    }

    if (totalPages <= 1) return null

    return (
        <div className="mt-6 flex gap-2 justify-center items-center text-sm">
            {/* Erste Seite + Auslassung */}
            {page > 1 && (
                <>
                    <button
                        onClick={() => goToPage(0)}
                        className="px-3 py-1.5 rounded-md border border-gray-300 text-gray-700 hover:bg-gray-100 transition"
                    >
                        1
                    </button>
                    <span className="text-gray-400">…</span>
                </>
            )}

            {/* Vorherige Seite */}
            {page > 0 && (
                <button
                    onClick={() => goToPage(page - 1)}
                    className="px-3 py-1.5 rounded-md border border-gray-300 text-gray-700 hover:bg-gray-100 transition"
                >
                    {page}
                </button>
            )}

            {/* Aktuelle Seite (kein Button) */}
            <span className="px-3 py-1.5 rounded-md bg-[#00a0a7] text-white font-semibold shadow-sm">
                {page + 1}
            </span>

            {/* Nächste Seite */}
            {page < totalPages - 1 && (
                <button
                    onClick={() => goToPage(page + 1)}
                    className="px-3 py-1.5 rounded-md border border-gray-300 text-gray-700 hover:bg-gray-100 transition"
                >
                    {page + 2}
                </button>
            )}

            {/* Letzte Seite + Auslassung */}
            {page < totalPages - 2 && (
                <>
                    <span className="text-gray-400">…</span>
                    <button
                        onClick={() => goToPage(totalPages - 1)}
                        className="px-3 py-1.5 rounded-md border border-gray-300 text-gray-700 hover:bg-gray-100 transition"
                    >
                        {totalPages}
                    </button>
                </>
            )}
        </div>
    )
}
