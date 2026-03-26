const stateColors = {
  NEW: { bg: '#e3f2fd', color: '#1565c0', label: 'New' },
  IN_PROGRESS: { bg: '#fff3e0', color: '#e65100', label: 'In Progress' },
  ON_HOLD: { bg: '#fce4ec', color: '#c62828', label: 'On Hold' },
  RESOLVED: { bg: '#e8f5e9', color: '#2e7d32', label: 'Resolved' },
  CLOSED: { bg: '#eceff1', color: '#546e7a', label: 'Closed' },
  CANCELLED: { bg: '#f3e5f5', color: '#6a1b9a', label: 'Cancelled' }
}

const priorityColors = {
  CRITICAL: { bg: '#ffebee', color: '#b71c1c', label: 'Critical' },
  HIGH: { bg: '#fff3e0', color: '#e65100', label: 'High' },
  MODERATE: { bg: '#fff8e1', color: '#f57f17', label: 'Moderate' },
  LOW: { bg: '#e8f5e9', color: '#2e7d32', label: 'Low' },
  PLANNING: { bg: '#eceff1', color: '#546e7a', label: 'Planning' }
}

export function StateBadge({ state }) {
  const config = stateColors[state] || { bg: '#eee', color: '#333', label: state }
  return (
    <span className="badge" style={{ backgroundColor: config.bg, color: config.color }}>
      {config.label}
    </span>
  )
}

export function PriorityBadge({ priority }) {
  const config = priorityColors[priority] || { bg: '#eee', color: '#333', label: priority }
  return (
    <span className="badge" style={{ backgroundColor: config.bg, color: config.color }}>
      {config.label}
    </span>
  )
}
