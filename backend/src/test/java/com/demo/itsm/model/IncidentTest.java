package com.demo.itsm.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Incident Model - OOP Principles Tests")
class IncidentTest {

    @Test
    @DisplayName("Incident inherits from Ticket and BaseEntity")
    void inheritance() {
        Incident incident = new Incident();
        assertInstanceOf(Ticket.class, incident, "Incident should extend Ticket");
        assertInstanceOf(BaseEntity.class, incident, "Incident should extend BaseEntity (via Ticket)");
    }

    @Test
    @DisplayName("Incident implements Assignable interface (Polymorphism)")
    void implementsAssignable() {
        Incident incident = new Incident();
        assertInstanceOf(Assignable.class, incident);

        incident.assignTo("John Doe");
        assertEquals("John Doe", incident.getAssignedTo());
    }

    @Test
    @DisplayName("Incident implements Auditable interface (Polymorphism)")
    void implementsAuditable() {
        Incident incident = new Incident();
        assertInstanceOf(Auditable.class, incident);

        incident.setCreatedBy("admin");
        assertEquals("admin", incident.getCreatedBy());
    }

    @Test
    @DisplayName("getNumberPrefix returns INC (Polymorphism)")
    void numberPrefix() {
        Incident incident = new Incident();
        assertEquals("INC", incident.getNumberPrefix());
    }

    @Test
    @DisplayName("getSlaHours varies by priority (Polymorphism)")
    void slaHoursByPriority() {
        Incident incident = new Incident();

        incident.setPriority(Priority.CRITICAL);
        assertEquals(4, incident.getSlaHours());

        incident.setPriority(Priority.HIGH);
        assertEquals(8, incident.getSlaHours());

        incident.setPriority(Priority.MODERATE);
        assertEquals(24, incident.getSlaHours());

        incident.setPriority(Priority.LOW);
        assertEquals(48, incident.getSlaHours());
    }

    @Test
    @DisplayName("ChangeRequest has different prefix and SLA (Polymorphism)")
    void changeRequestPolymorphism() {
        ChangeRequest cr = new ChangeRequest();
        assertEquals("CHG", cr.getNumberPrefix());

        cr.setChangeType(ChangeRequest.ChangeType.EMERGENCY);
        assertEquals(4, cr.getSlaHours());

        cr.setChangeType(ChangeRequest.ChangeType.NORMAL);
        assertEquals(48, cr.getSlaHours());
    }

    @Test
    @DisplayName("Encapsulation - private fields accessed via getters/setters")
    void encapsulation() {
        Incident incident = new Incident();
        incident.setShortDescription("Test");
        incident.setPriority(Priority.HIGH);
        incident.setState(IncidentState.NEW);

        assertEquals("Test", incident.getShortDescription());
        assertEquals(Priority.HIGH, incident.getPriority());
        assertEquals(IncidentState.NEW, incident.getState());
    }

    @Test
    @DisplayName("getDisplayName combines number and description (Abstraction)")
    void displayName() {
        Incident incident = new Incident();
        incident.setNumber("INC0000001");
        incident.setShortDescription("Test incident");

        assertEquals("INC0000001 - Test incident", incident.getDisplayName());
    }
}
