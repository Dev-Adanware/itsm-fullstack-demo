package com.demo.itsm.dto;

import com.demo.itsm.model.Incident;
import com.demo.itsm.model.IncidentState;
import com.demo.itsm.model.Priority;

import java.time.LocalDateTime;

/**
 * OOP: ENCAPSULATION - DTO controls what data the client receives.
 * Exposes only what the API consumer needs, hiding internal entity details.
 */
public class IncidentResponse {

    private Long id;
    private String number;
    private String shortDescription;
    private String description;
    private Priority priority;
    private IncidentState state;
    private String category;
    private String subcategory;
    private String assignedTo;
    private String assignmentGroup;
    private String caller;
    private int impact;
    private int urgency;
    private int slaHours;
    private String resolutionNotes;
    private String createdBy;
    private String updatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /**
     * Factory method - converts domain entity to response DTO.
     */
    public static IncidentResponse fromEntity(Incident incident) {
        IncidentResponse dto = new IncidentResponse();
        dto.id = incident.getId();
        dto.number = incident.getNumber();
        dto.shortDescription = incident.getShortDescription();
        dto.description = incident.getDescription();
        dto.priority = incident.getPriority();
        dto.state = incident.getState();
        dto.category = incident.getCategory();
        dto.subcategory = incident.getSubcategory();
        dto.assignedTo = incident.getAssignedTo();
        dto.assignmentGroup = incident.getAssignmentGroup();
        dto.caller = incident.getCaller();
        dto.impact = incident.getImpact();
        dto.urgency = incident.getUrgency();
        dto.slaHours = incident.getSlaHours();
        dto.resolutionNotes = incident.getResolutionNotes();
        dto.createdBy = incident.getCreatedBy();
        dto.updatedBy = incident.getUpdatedBy();
        dto.createdAt = incident.getCreatedAt();
        dto.updatedAt = incident.getUpdatedAt();
        return dto;
    }

    // --- Getters ---

    public Long getId() { return id; }
    public String getNumber() { return number; }
    public String getShortDescription() { return shortDescription; }
    public String getDescription() { return description; }
    public Priority getPriority() { return priority; }
    public IncidentState getState() { return state; }
    public String getCategory() { return category; }
    public String getSubcategory() { return subcategory; }
    public String getAssignedTo() { return assignedTo; }
    public String getAssignmentGroup() { return assignmentGroup; }
    public String getCaller() { return caller; }
    public int getImpact() { return impact; }
    public int getUrgency() { return urgency; }
    public int getSlaHours() { return slaHours; }
    public String getResolutionNotes() { return resolutionNotes; }
    public String getCreatedBy() { return createdBy; }
    public String getUpdatedBy() { return updatedBy; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
