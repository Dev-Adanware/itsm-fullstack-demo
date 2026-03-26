package com.demo.itsm.model;

/**
 * OOP: POLYMORPHISM (Interface)
 * Entities that track who created/updated them implement this interface.
 * Demonstrates interface segregation - not all entities need auditing.
 */
public interface Auditable {

    String getCreatedBy();

    void setCreatedBy(String user);

    String getUpdatedBy();

    void setUpdatedBy(String user);
}
