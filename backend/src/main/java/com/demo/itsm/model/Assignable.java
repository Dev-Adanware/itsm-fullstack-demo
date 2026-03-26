package com.demo.itsm.model;

/**
 * OOP: POLYMORPHISM (Interface)
 * Any entity that can be assigned to a user implements this interface.
 * Different ticket types can implement assignment differently.
 */
public interface Assignable {

    String getAssignedTo();

    void assignTo(String assignee);

    String getAssignmentGroup();

    void setAssignmentGroup(String group);
}
