package com.demo.itsm.model;

import jakarta.persistence.*;

/**
 * OOP: INHERITANCE - Concrete class extending abstract Ticket.
 * Represents a ServiceNow Incident record with additional incident-specific fields.
 * Inherits common ticket behavior and adds incident-specific state management.
 */
@Entity
@Table(name = "incidents")
public class Incident extends Ticket {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private IncidentState state = IncidentState.NEW;

    @Column(name = "category")
    private String category;

    @Column(name = "subcategory")
    private String subcategory;

    @Column(name = "impact")
    private int impact = 3;

    @Column(name = "urgency")
    private int urgency = 3;

    @Column(name = "resolution_notes", columnDefinition = "TEXT")
    private String resolutionNotes;

    @Column(name = "caller")
    private String caller;

    // --- Abstract method implementations (Polymorphism) ---

    /**
     * OOP: POLYMORPHISM - Incident uses "INC" prefix.
     */
    @Override
    public String getNumberPrefix() {
        return "INC";
    }

    /**
     * OOP: POLYMORPHISM - SLA hours depend on priority.
     * Critical = 4h, High = 8h, Moderate = 24h, Low = 48h, Planning = 72h.
     */
    @Override
    public int getSlaHours() {
        return switch (getPriority()) {
            case CRITICAL -> 4;
            case HIGH -> 8;
            case MODERATE -> 24;
            case LOW -> 48;
            case PLANNING -> 72;
        };
    }

    /**
     * OOP: ABSTRACTION - Implementation of display name from BaseEntity.
     */
    @Override
    public String getDisplayName() {
        return getNumber() + " - " + getShortDescription();
    }

    // --- Encapsulated fields with getters/setters ---

    public IncidentState getState() {
        return state;
    }

    public void setState(IncidentState state) {
        this.state = state;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    public int getImpact() {
        return impact;
    }

    public void setImpact(int impact) {
        this.impact = impact;
    }

    public int getUrgency() {
        return urgency;
    }

    public void setUrgency(int urgency) {
        this.urgency = urgency;
    }

    public String getResolutionNotes() {
        return resolutionNotes;
    }

    public void setResolutionNotes(String resolutionNotes) {
        this.resolutionNotes = resolutionNotes;
    }

    public String getCaller() {
        return caller;
    }

    public void setCaller(String caller) {
        this.caller = caller;
    }
}
