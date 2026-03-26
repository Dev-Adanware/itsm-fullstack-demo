package com.demo.itsm.model;

/**
 * ServiceNow incident lifecycle states.
 */
public enum IncidentState {
    NEW("New"),
    IN_PROGRESS("In Progress"),
    ON_HOLD("On Hold"),
    RESOLVED("Resolved"),
    CLOSED("Closed"),
    CANCELLED("Cancelled");

    private final String label;

    IncidentState(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
