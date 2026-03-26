package com.demo.itsm.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

/**
 * OOP: INHERITANCE + ABSTRACTION
 * Abstract ticket class that extends BaseEntity and implements Assignable & Auditable.
 * Serves as the base for all ticket types (Incident, ChangeRequest, etc.).
 * Demonstrates multiple interface implementation (Polymorphism).
 */
@MappedSuperclass
public abstract class Ticket extends BaseEntity implements Assignable, Auditable {

    @Column(unique = true, nullable = false)
    private String number;

    @NotBlank(message = "Short description is required")
    @Column(name = "short_description", nullable = false)
    private String shortDescription;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Priority priority = Priority.MODERATE;

    @Column(name = "assigned_to")
    private String assignedTo;

    @Column(name = "assignment_group")
    private String assignmentGroup;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;

    /**
     * OOP: ABSTRACTION - Each ticket type defines its own prefix (INC, CHG, etc.)
     */
    public abstract String getNumberPrefix();

    /**
     * OOP: POLYMORPHISM - Each ticket type calculates SLA differently.
     */
    public abstract int getSlaHours();

    // --- Assignable interface implementation (Polymorphism) ---

    @Override
    public String getAssignedTo() {
        return assignedTo;
    }

    @Override
    public void assignTo(String assignee) {
        this.assignedTo = assignee;
    }

    @Override
    public String getAssignmentGroup() {
        return assignmentGroup;
    }

    @Override
    public void setAssignmentGroup(String group) {
        this.assignmentGroup = group;
    }

    // --- Auditable interface implementation (Polymorphism) ---

    @Override
    public String getCreatedBy() {
        return createdBy;
    }

    @Override
    public void setCreatedBy(String user) {
        this.createdBy = user;
    }

    @Override
    public String getUpdatedBy() {
        return updatedBy;
    }

    @Override
    public void setUpdatedBy(String user) {
        this.updatedBy = user;
    }

    // --- Encapsulated fields ---

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }
}
