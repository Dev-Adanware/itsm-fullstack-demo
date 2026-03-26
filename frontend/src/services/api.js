const API_BASE = (window.location.hostname === 'localhost' || window.location.hostname === '127.0.0.1')
  ? 'http://localhost:8080/api/incidents'
  : '/api/incidents';

async function handleResponse(response) {
  if (!response.ok) {
    const error = await response.json().catch(() => ({ error: 'Request failed' }));
    throw new Error(error.error || `HTTP ${response.status}`);
  }
  return response.json();
}

export const api = {
  getAll: (state, priority) => {
    const params = new URLSearchParams();
    if (state) params.set('state', state);
    if (priority) params.set('priority', priority);
    const query = params.toString();
    return fetch(`${API_BASE}${query ? '?' + query : ''}`).then(handleResponse);
  },

  getById: (id) =>
    fetch(`${API_BASE}/${id}`).then(handleResponse),

  create: (data) =>
    fetch(API_BASE, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(data)
    }).then(handleResponse),

  update: (id, data) =>
    fetch(`${API_BASE}/${id}`, {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(data)
    }).then(handleResponse),

  delete: (id) =>
    fetch(`${API_BASE}/${id}`, { method: 'DELETE' }),

  transitionState: (id, newState) =>
    fetch(`${API_BASE}/${id}/state?newState=${newState}`, { method: 'PATCH' }).then(handleResponse),

  resolve: (id, resolutionNotes) =>
    fetch(`${API_BASE}/${id}/resolve`, {
      method: 'PATCH',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ resolutionNotes })
    }).then(handleResponse),

  getAllowedTransitions: (id) =>
    fetch(`${API_BASE}/${id}/transitions`).then(handleResponse),

  getStatistics: () =>
    fetch(`${API_BASE}/statistics`).then(handleResponse)
};
