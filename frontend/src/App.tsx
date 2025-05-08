import { useEffect, useState } from 'react'
import { getText } from './utils/api'

export default function App() {
    const [message, setMessage] = useState<string | null>(null)
    const [error, setError] = useState(false)

    useEffect(() => {
        getText('/api/hello')
            .then(setMessage)
            .catch(() => {
                setError(true)
                setMessage(null)
            })
    }, [])

    return (
        <div className="h-screen w-screen flex items-center justify-center bg-slate-100">
            <div className="bg-white shadow-lg rounded-xl p-8 max-w-md w-full text-center">
                <h1 className="text-4xl font-bold text-blue-600 mb-4">🎉 Hello World</h1>
                <p className="text-gray-700 text-lg">
                    {error
                        ? 'Fehler beim Laden der Nachricht 😢'
                        : message ?? 'Lade Nachricht...'}
                </p>
            </div>
        </div>
    )
}
