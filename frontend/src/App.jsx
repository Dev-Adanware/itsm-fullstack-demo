import { Routes, Route, Link, useLocation } from 'react-router-dom'
import Dashboard from './components/Dashboard'
import IncidentList from './components/IncidentList'
import IncidentForm from './components/IncidentForm'
import IncidentDetail from './components/IncidentDetail'

function App() {
  const location = useLocation()

  const isActive = (path) => {
    if (path === '/' && location.pathname === '/') return true
    if (path !== '/' && location.pathname.startsWith(path)) return true
    return false
  }

  return (
    <div className="app">
      <header className="header">
        <div className="header-brand">
          <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
            <circle cx="12" cy="12" r="10" />
            <path d="M12 6v6l4 2" />
          </svg>
          <h1>ServiceNow ITSM</h1>
        </div>
        <nav className="header-nav">
          <Link to="/" className={isActive('/') && !isActive('/incidents') ? 'active' : ''}>Dashboard</Link>
          <Link to="/incidents" className={isActive('/incidents') ? 'active' : ''}>Incidents</Link>
          <a href="http://localhost:8080/swagger-ui.html" target="_blank" rel="noopener noreferrer">API Docs</a>
        </nav>
      </header>

      <main className="main">
        <Routes>
          <Route path="/" element={<Dashboard />} />
          <Route path="/incidents" element={<IncidentList />} />
          <Route path="/incidents/new" element={<IncidentForm />} />
          <Route path="/incidents/:id" element={<IncidentDetail />} />
          <Route path="/incidents/:id/edit" element={<IncidentForm />} />
        </Routes>
      </main>

      <footer className="footer">
        <p>ServiceNow ITSM Demo &mdash; OOP Principles Showcase</p>
      </footer>
    </div>
  )
}

export default App
