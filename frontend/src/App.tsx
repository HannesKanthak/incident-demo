import { BrowserRouter, Routes, Route } from 'react-router-dom'
import Hello from './pages/Hello'
import IncidentPage from "./pages/IncidentPage.tsx";

export default function App() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/hello" element={<Hello />} />
                <Route path="/" element={<IncidentPage />} />
            </Routes>
        </BrowserRouter>
    )
}
