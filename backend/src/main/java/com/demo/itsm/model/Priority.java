package com.demo.itsm.model;

/**
 * ServiceNow-style priority levels (1 = Critical, 5 = Planning).
 */
public enum Priority {
    CRITICAL(1, "Critical"),
    HIGH(2, "High"),
    MODERATE(3, "Moderate"),
    LOW(4, "Low"),
    PLANNING(5, "Planning");

    private final int value;
    private final String label;

    Priority(int value, String label) {
        this.value = value;
        this.label = label;
    }

    public int getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }
}
