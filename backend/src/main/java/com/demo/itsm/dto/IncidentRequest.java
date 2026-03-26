package com.demo.itsm.dto;

import com.demo.itsm.model.Priority;
import jakarta.validation.constraints.NotBlank;

/**
 * OOP: ENCAPSULATION - DTO controls what data the client can send.
 * Separates external API contract from internal domain model.
 */
public class IncidentRequest {

    @NotBlank(message = "Short description is required")
    private String shortDescription;

    private String description;
    private Priority priority;
    private String category;
    private String subcategory;
    private String assignedTo;
    private String assignmentGroup;
    private String caller;
    private int impact = 3;
    private int urgency = 3;

    public IncidentRequest() {}

    // --- Builder pattern for fluent construction ---

    public static Builder builder() {
        return new Builder();
    }

    /**
     * OOP: DESIGN PATTERN - Builder Pattern
     * Demonstrates clean object construction with many optional fields.
     */
    public static class Builder {
        private final IncidentRequest request = new IncidentRequest();

        public Builder shortDescription(String val) { request.shortDescription = val; return this; }
        public Builder description(String val) { request.description = val; return this; }
        public Builder priority(Priority val) { request.priority = val; return this; }
        public Builder category(String val) { request.category = val; return this; }
        public Builder subcategory(String val) { request.subcategory = val; return this; }
        public Builder assignedTo(String val) { request.assignedTo = val; return this; }
        public Builder assignmentGroup(String val) { request.assignmentGroup = val; return this; }
        public Builder caller(String val) { request.caller = val; return this; }
        public Builder impact(int val) { request.impact = val; return this; }
        public Builder urgency(int val) { request.urgency = val; return this; }

        public IncidentRequest build() { return request; }
    }

    // --- Getters and Setters ---

    public String getShortDescription() { return shortDescription; }
    public void setShortDescription(String shortDescription) { this.shortDescription = shortDescription; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Priority getPriority() { return priority; }
    public void setPriority(Priority priority) { this.priority = priority; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getSubcategory() { return subcategory; }
    public void setSubcategory(String subcategory) { this.subcategory = subcategory; }

    public String getAssignedTo() { return assignedTo; }
    public void setAssignedTo(String assignedTo) { this.assignedTo = assignedTo; }

    public String getAssignmentGroup() { return assignmentGroup; }
    public void setAssignmentGroup(String assignmentGroup) { this.assignmentGroup = assignmentGroup; }

    public String getCaller() { return caller; }
    public void setCaller(String caller) { this.caller = caller; }

    public int getImpact() { return impact; }
    public void setImpact(int impact) { this.impact = impact; }

    public int getUrgency() { return urgency; }
    public void setUrgency(int urgency) { this.urgency = urgency; }
}
