import { useState, useEffect } from 'react'
import { useParams, Link, useNavigate } from 'react-router-dom'
import { api } from '../services/api'

function IncidentDetail() {
  const { id } = useParams()
  const navigate = useNavigate()
  const [incident, setIncident] = useState(null)
  const [transitions, setTransitions] = useState([])
  const [resolveNotes, setResolveNotes] = useState('')
  const [showResolve, setShowResolve] = useState(false)
  const [error, setError] = useState(null)
  const [confirmDelete, setConfirmDelete] = useState(false)

  useEffect(() => {
    loadIncident()
  }, [id])

  const loadIncident = async () => {
    try {
      const [inc, trans] = await Promise.all([
        api.getById(id),
        api.getAllowedTransitions(id)
      ])
      setIncident(inc)
      setTransitions(trans)
      setError(null)
    } catch (err) {
      setError(err.message)
    }
  }

  const handleTransition = async (newState) => {
    try {
      if (newState === 'RESOLVED') {
        setShowResolve(true)
        return
      }
      await api.transitionState(id, newState)
      loadIncident()
    } catch (err) {
      setError(err.message)
    }
  }

  const handleResolve = async () => {
    try {
      await api.resolve(id, resolveNotes)
      setShowResolve(false)
      setResolveNotes('')
      loadIncident()
    } catch (err) {
      setError(err.message)
    }
  }

  const handleDelete = async () => {
    try {
      await api.delete(id)
      navigate('/incidents')
    } catch (err) {
      setError(err.message)
    }
  }

  if (error && !incident) return <div className="error-message">Error: {error}</div>
  if (!incident) return <div className="loading">Loading...</div>

  return (
    <div className="incident-detail">
      <div className="page-header">
        <h2>{incident.number}</h2>
        <div className="header-actions">
          <Link to={`/incidents/${id}/edit`} className="btn btn-secondary">Edit</Link>
          <Link to="/incidents" className="btn btn-secondary">Back to List</Link>
        </div>
      </div>

      {error && <div className="error-message">{error}</div>}

      <div className="detail-grid">
        <div className="detail-main">
          <div className="card">
            <h3>Incident Details</h3>
            <div className="detail-fields">
              <div className="field">
                <label>Number</label>
                <span>{incident.number}</span>
              </div>
              <div className="field">
                <label>State</label>
                <span className={`badge badge-state-${incident.state.toLowerCase().replace('_', '-')}`}>
                  {incident.state.replace('_', ' ')}
                </span>
              </div>
              <div className="field">
                <label>Priority</label>
                <span className={`badge badge-priority-${incident.priority.toLowerCase()}`}>
                  {incident.priority}
                </span>
              </div>
              <div className="field">
                <label>SLA</label>
                <span>{incident.slaHours} hours</span>
              </div>
              <div className="field full-width">
                <label>Short Description</label>
                <span>{incident.shortDescription}</span>
              </div>
              <div className="field full-width">
                <label>Description</label>
                <span>{incident.description || '—'}</span>
              </div>
              <div className="field">
                <label>Category</label>
                <span>{incident.category || '—'}</span>
              </div>
              <div className="field">
                <label>Subcategory</label>
                <span>{incident.subcategory || '—'}</span>
              </div>
              <div className="field">
                <label>Impact</label>
                <span>{incident.impact}</span>
              </div>
              <div className="field">
                <label>Urgency</label>
                <span>{incident.urgency}</span>
              </div>
              <div className="field">
                <label>Caller</label>
                <span>{incident.caller || '—'}</span>
              </div>
              <div className="field">
                <label>Assigned To</label>
                <span>{incident.assignedTo || '—'}</span>
              </div>
              <div className="field">
                <label>Assignment Group</label>
                <span>{incident.assignmentGroup || '—'}</span>
              </div>
              <div className="field">
                <label>Created By</label>
                <span>{incident.createdBy || '—'}</span>
              </div>
              <div className="field">
                <label>Created At</label>
                <span>{incident.createdAt ? new Date(incident.createdAt).toLocaleString() : '—'}</span>
              </div>
              <div className="field">
                <label>Updated At</label>
                <span>{incident.updatedAt ? new Date(incident.updatedAt).toLocaleString() : '—'}</span>
              </div>
              {incident.resolutionNotes && (
                <div className="field full-width">
                  <label>Resolution Notes</label>
                  <span>{incident.resolutionNotes}</span>
                </div>
              )}
            </div>
          </div>
        </div>

        <div className="detail-sidebar">
          <div className="card">
            <h3>State Transitions</h3>
            <p className="help-text">Available transitions from current state:</p>
            {transitions.length === 0 ? (
              <p className="help-text">No transitions available (terminal state)</p>
            ) : (
              <div className="transition-buttons">
                {transitions.map(state => (
                  <button
                    key={state}
                    onClick={() => handleTransition(state)}
                    className={`btn btn-transition btn-state-${state.toLowerCase().replace('_', '-')}`}
                  >
                    {state.replace('_', ' ')}
                  </button>
                ))}
              </div>
            )}
          </div>

          {showResolve && (
            <div className="card resolve-card">
              <h3>Resolve Incident</h3>
              <textarea
                value={resolveNotes}
                onChange={e => setResolveNotes(e.target.value)}
                placeholder="Enter resolution notes..."
                rows={4}
              />
              <div className="resolve-actions">
                <button onClick={handleResolve} className="btn btn-primary">Confirm Resolve</button>
                <button onClick={() => setShowResolve(false)} className="btn btn-secondary">Cancel</button>
              </div>
            </div>
          )}
        </div>
      </div>

      <div className="danger-zone">
        <h4>Danger Zone</h4>
        {!confirmDelete ? (
          <button onClick={() => setConfirmDelete(true)} className="btn btn-outline-danger">Delete this incident</button>
        ) : (
          <div className="confirm-delete">
            <p>Are you sure? This action cannot be undone.</p>
            <button onClick={handleDelete} className="btn btn-danger">Yes, delete permanently</button>
            <button onClick={() => setConfirmDelete(false)} className="btn btn-secondary">Cancel</button>
          </div>
        )}
      </div>
    </div>
  )
}

export default IncidentDetail
