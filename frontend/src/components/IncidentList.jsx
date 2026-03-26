import { useState, useEffect } from 'react'
import { Link } from 'react-router-dom'
import { api } from '../services/api'

function IncidentList() {
  const [incidents, setIncidents] = useState([])
  const [filterState, setFilterState] = useState('')
  const [filterPriority, setFilterPriority] = useState('')
  const [error, setError] = useState(null)
  const [loading, setLoading] = useState(true)

  const states = ['NEW', 'IN_PROGRESS', 'ON_HOLD', 'RESOLVED', 'CLOSED', 'CANCELLED']
  const priorities = ['CRITICAL', 'HIGH', 'MODERATE', 'LOW', 'PLANNING']

  useEffect(() => {
    loadIncidents()
  }, [filterState, filterPriority])

  const loadIncidents = () => {
    setLoading(true)
    api.getAll(filterState || null, filterPriority || null)
      .then(data => { setIncidents(data); setError(null) })
      .catch(err => setError(err.message))
      .finally(() => setLoading(false))
  }

  const handleDelete = async (id) => {
    if (!window.confirm('Are you sure you want to delete this incident?')) return
    try {
      await api.delete(id)
      loadIncidents()
    } catch (err) {
      setError(err.message)
    }
  }

  return (
    <div className="incident-list">
      <div className="page-header">
        <h2>Incidents</h2>
        <Link to="/incidents/new" className="btn btn-primary">Create Incident</Link>
      </div>

      <div className="filters">
        <select value={filterState} onChange={e => { setFilterState(e.target.value); setFilterPriority('') }}>
          <option value="">All States</option>
          {states.map(s => <option key={s} value={s}>{s.replace('_', ' ')}</option>)}
        </select>
        <select value={filterPriority} onChange={e => { setFilterPriority(e.target.value); setFilterState('') }}>
          <option value="">All Priorities</option>
          {priorities.map(p => <option key={p} value={p}>{p}</option>)}
        </select>
        <span className="filter-count">{incidents.length} incident(s)</span>
      </div>

      {error && <div className="error-message">{error}</div>}
      {loading && <div className="loading">Loading...</div>}

      {!loading && (
        <table className="table">
          <thead>
            <tr>
              <th>Number</th>
              <th>Short Description</th>
              <th>Priority</th>
              <th>State</th>
              <th>Category</th>
              <th>Assigned To</th>
              <th>SLA (hrs)</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {incidents.length === 0 ? (
              <tr><td colSpan="8" className="empty">No incidents found</td></tr>
            ) : (
              incidents.map(inc => (
                <tr key={inc.id}>
                  <td><Link to={`/incidents/${inc.id}`}>{inc.number}</Link></td>
                  <td>{inc.shortDescription}</td>
                  <td><span className={`badge badge-priority-${inc.priority.toLowerCase()}`}>{inc.priority}</span></td>
                  <td><span className={`badge badge-state-${inc.state.toLowerCase().replace('_', '-')}`}>{inc.state.replace('_', ' ')}</span></td>
                  <td>{inc.category || '—'}</td>
                  <td>{inc.assignedTo || '—'}</td>
                  <td>{inc.slaHours}</td>
                  <td className="actions">
                    <Link to={`/incidents/${inc.id}`} className="btn btn-small">View</Link>
                    <Link to={`/incidents/${inc.id}/edit`} className="btn btn-small">Edit</Link>
                  </td>
                </tr>
              ))
            )}
          </tbody>
        </table>
      )}
    </div>
  )
}

export default IncidentList
