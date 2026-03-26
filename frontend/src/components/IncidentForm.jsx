import { useState, useEffect } from 'react'
import { useParams, useNavigate, Link } from 'react-router-dom'
import { api } from '../services/api'

const emptyForm = {
  shortDescription: '',
  description: '',
  priority: 'MODERATE',
  category: '',
  subcategory: '',
  assignedTo: '',
  assignmentGroup: '',
  caller: '',
  impact: 3,
  urgency: 3
}

function IncidentForm() {
  const { id } = useParams()
  const navigate = useNavigate()
  const isEdit = Boolean(id)

  const [form, setForm] = useState(emptyForm)
  const [error, setError] = useState(null)
  const [saving, setSaving] = useState(false)

  useEffect(() => {
    if (isEdit) {
      api.getById(id)
        .then(data => setForm({
          shortDescription: data.shortDescription || '',
          description: data.description || '',
          priority: data.priority || 'MODERATE',
          category: data.category || '',
          subcategory: data.subcategory || '',
          assignedTo: data.assignedTo || '',
          assignmentGroup: data.assignmentGroup || '',
          caller: data.caller || '',
          impact: data.impact || 3,
          urgency: data.urgency || 3
        }))
        .catch(err => setError(err.message))
    }
  }, [id, isEdit])

  const handleChange = (e) => {
    const { name, value } = e.target
    setForm(prev => ({ ...prev, [name]: value }))
  }

  const handleSubmit = async (e) => {
    e.preventDefault()
    setSaving(true)
    setError(null)

    try {
      const data = {
        ...form,
        impact: parseInt(form.impact),
        urgency: parseInt(form.urgency)
      }

      if (isEdit) {
        await api.update(id, data)
        navigate(`/incidents/${id}`)
      } else {
        const created = await api.create(data)
        navigate(`/incidents/${created.id}`)
      }
    } catch (err) {
      setError(err.message)
    } finally {
      setSaving(false)
    }
  }

  const priorities = ['CRITICAL', 'HIGH', 'MODERATE', 'LOW', 'PLANNING']
  const categories = ['Software', 'Hardware', 'Network', 'Security', 'Database']

  return (
    <div className="incident-form">
      <div className="page-header">
        <h2>{isEdit ? 'Edit Incident' : 'Create New Incident'}</h2>
        <Link to={isEdit ? `/incidents/${id}` : '/incidents'} className="btn btn-secondary">Cancel</Link>
      </div>

      {error && <div className="error-message">{error}</div>}

      <form onSubmit={handleSubmit} className="card form-card">
        <div className="form-grid">
          <div className="form-group full-width">
            <label htmlFor="shortDescription">Short Description *</label>
            <input
              id="shortDescription"
              name="shortDescription"
              type="text"
              value={form.shortDescription}
              onChange={handleChange}
              required
              placeholder="Brief summary of the incident"
            />
          </div>

          <div className="form-group full-width">
            <label htmlFor="description">Description</label>
            <textarea
              id="description"
              name="description"
              value={form.description}
              onChange={handleChange}
              rows={4}
              placeholder="Detailed description of the incident"
            />
          </div>

          <div className="form-group">
            <label htmlFor="priority">Priority</label>
            <select id="priority" name="priority" value={form.priority} onChange={handleChange}>
              {priorities.map(p => <option key={p} value={p}>{p}</option>)}
            </select>
          </div>

          <div className="form-group">
            <label htmlFor="category">Category</label>
            <select id="category" name="category" value={form.category} onChange={handleChange}>
              <option value="">Select...</option>
              {categories.map(c => <option key={c} value={c}>{c}</option>)}
            </select>
          </div>

          <div className="form-group">
            <label htmlFor="subcategory">Subcategory</label>
            <input
              id="subcategory"
              name="subcategory"
              type="text"
              value={form.subcategory}
              onChange={handleChange}
              placeholder="e.g. Email, VPN, Printer"
            />
          </div>

          <div className="form-group">
            <label htmlFor="caller">Caller</label>
            <input
              id="caller"
              name="caller"
              type="text"
              value={form.caller}
              onChange={handleChange}
              placeholder="Person reporting the issue"
            />
          </div>

          <div className="form-group">
            <label htmlFor="assignmentGroup">Assignment Group</label>
            <input
              id="assignmentGroup"
              name="assignmentGroup"
              type="text"
              value={form.assignmentGroup}
              onChange={handleChange}
              placeholder="e.g. IT Infrastructure"
            />
          </div>

          <div className="form-group">
            <label htmlFor="assignedTo">Assigned To</label>
            <input
              id="assignedTo"
              name="assignedTo"
              type="text"
              value={form.assignedTo}
              onChange={handleChange}
              placeholder="Person assigned to resolve"
            />
          </div>

          <div className="form-group">
            <label htmlFor="impact">Impact (1-3)</label>
            <select id="impact" name="impact" value={form.impact} onChange={handleChange}>
              <option value="1">1 - High</option>
              <option value="2">2 - Medium</option>
              <option value="3">3 - Low</option>
            </select>
          </div>

          <div className="form-group">
            <label htmlFor="urgency">Urgency (1-3)</label>
            <select id="urgency" name="urgency" value={form.urgency} onChange={handleChange}>
              <option value="1">1 - High</option>
              <option value="2">2 - Medium</option>
              <option value="3">3 - Low</option>
            </select>
          </div>
        </div>

        <div className="form-actions">
          <button type="submit" className="btn btn-primary" disabled={saving}>
            {saving ? 'Saving...' : (isEdit ? 'Update Incident' : 'Create Incident')}
          </button>
          <Link to={isEdit ? `/incidents/${id}` : '/incidents'} className="btn btn-secondary">Cancel</Link>
        </div>
      </form>
    </div>
  )
}

export default IncidentForm
