package com.demo.itsm.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * OOP: INHERITANCE - Another concrete Ticket subclass.
 * Demonstrates how the same base class (Ticket) can produce different behaviors.
 * ChangeRequest has different SLA calculation and its own specific fields.
 */
@Entity
@Table(name = "change_requests")
public class ChangeRequest extends Ticket {

    public enum ChangeState {
        NEW, ASSESS, AUTHORIZE, SCHEDULED, IMPLEMENT, REVIEW, CLOSED, CANCELLED
    }

    public enum ChangeType {
        STANDARD, NORMAL, EMERGENCY
    }

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ChangeState state = ChangeState.NEW;

    @Enumerated(EnumType.STRING)
    @Column(name = "change_type", nullable = false)
    private ChangeType changeType = ChangeType.NORMAL;

    @Column(name = "planned_start")
    private LocalDateTime plannedStart;

    @Column(name = "planned_end")
    private LocalDateTime plannedEnd;

    @Column(name = "justification", columnDefinition = "TEXT")
    private String justification;

    @Column(name = "risk_assessment", columnDefinition = "TEXT")
    private String riskAssessment;

    /**
     * OOP: POLYMORPHISM - ChangeRequest uses "CHG" prefix.
     */
    @Override
    public String getNumberPrefix() {
        return "CHG";
    }

    /**
     * OOP: POLYMORPHISM - Change requests have different SLA based on type.
     * Emergency = 4h, Normal = 48h, Standard = 0 (pre-approved).
     */
    @Override
    public int getSlaHours() {
        return switch (changeType) {
            case EMERGENCY -> 4;
            case NORMAL -> 48;
            case STANDARD -> 0;
        };
    }

    @Override
    public String getDisplayName() {
        return getNumber() + " - " + getShortDescription();
    }

    // --- Encapsulated fields ---

    public ChangeState getState() {
        return state;
    }

    public void setState(ChangeState state) {
        this.state = state;
    }

    public ChangeType getChangeType() {
        return changeType;
    }

    public void setChangeType(ChangeType changeType) {
        this.changeType = changeType;
    }

    public LocalDateTime getPlannedStart() {
        return plannedStart;
    }

    public void setPlannedStart(LocalDateTime plannedStart) {
        this.plannedStart = plannedStart;
    }

    public LocalDateTime getPlannedEnd() {
        return plannedEnd;
    }

    public void setPlannedEnd(LocalDateTime plannedEnd) {
        this.plannedEnd = plannedEnd;
    }

    public String getJustification() {
        return justification;
    }

    public void setJustification(String justification) {
        this.justification = justification;
    }

    public String getRiskAssessment() {
        return riskAssessment;
    }

    public void setRiskAssessment(String riskAssessment) {
        this.riskAssessment = riskAssessment;
    }
}
