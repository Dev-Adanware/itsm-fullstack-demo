import { useState, useEffect } from 'react'
import { Link } from 'react-router-dom'
import { api } from '../services/api'

function Dashboard() {
  const [stats, setStats] = useState(null)
  const [recent, setRecent] = useState([])
  const [error, setError] = useState(null)

  useEffect(() => {
    Promise.all([api.getStatistics(), api.getAll()])
      .then(([statsData, incidents]) => {
        setStats(statsData)
        setRecent(incidents.slice(0, 5))
      })
      .catch(err => setError(err.message))
  }, [])

  if (error) return <div className="error-message">Error: {error}</div>
  if (!stats) return <div className="loading">Loading dashboard...</div>

  return (
    <div className="dashboard">
      <div className="page-header">
        <h2>Dashboard</h2>
        <Link to="/incidents/new" className="btn btn-primary">Create Incident</Link>
      </div>

      <div className="stats-grid">
        <div className="stat-card">
          <div className="stat-number">{stats.total}</div>
          <div className="stat-label">Total Incidents</div>
        </div>
        <div className="stat-card stat-new">
          <div className="stat-number">{stats.new}</div>
          <div className="stat-label">New</div>
        </div>
        <div className="stat-card stat-progress">
          <div className="stat-number">{stats.inProgress}</div>
          <div className="stat-label">In Progress</div>
        </div>
        <div className="stat-card stat-hold">
          <div className="stat-number">{stats.onHold}</div>
          <div className="stat-label">On Hold</div>
        </div>
        <div className="stat-card stat-resolved">
          <div className="stat-number">{stats.resolved}</div>
          <div className="stat-label">Resolved</div>
        </div>
        <div className="stat-card stat-critical">
          <div className="stat-number">{stats.critical}</div>
          <div className="stat-label">Critical</div>
        </div>
        <div className="stat-card stat-high">
          <div className="stat-number">{stats.high}</div>
          <div className="stat-label">High Priority</div>
        </div>
        <div className="stat-card stat-closed">
          <div className="stat-number">{stats.closed}</div>
          <div className="stat-label">Closed</div>
        </div>
      </div>

      <div className="section">
        <h3>Recent Incidents</h3>
        <table className="table">
          <thead>
            <tr>
              <th>Number</th>
              <th>Short Description</th>
              <th>Priority</th>
              <th>State</th>
              <th>Assigned To</th>
            </tr>
          </thead>
          <tbody>
            {recent.map(inc => (
              <tr key={inc.id}>
                <td><Link to={`/incidents/${inc.id}`}>{inc.number}</Link></td>
                <td>{inc.shortDescription}</td>
                <td><span className={`badge badge-priority-${inc.priority.toLowerCase()}`}>{inc.priority}</span></td>
                <td><span className={`badge badge-state-${inc.state.toLowerCase().replace('_', '-')}`}>{inc.state.replace('_', ' ')}</span></td>
                <td>{inc.assignedTo || '—'}</td>
              </tr>
            ))}
          </tbody>
        </table>
        <Link to="/incidents" className="btn btn-secondary" style={{ marginTop: '1rem' }}>View All Incidents</Link>
      </div>
    </div>
  )
}

export default Dashboard
