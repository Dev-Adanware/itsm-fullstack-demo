package com.demo.itsm.service;

import com.demo.itsm.model.IncidentState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("IncidentStateTransition - Strategy Pattern Tests")
class IncidentStateTransitionTest {

    private IncidentStateTransition transition;

    @BeforeEach
    void setUp() {
        transition = new IncidentStateTransition();
    }

    @Test
    @DisplayName("NEW can transition to IN_PROGRESS")
    void newToInProgress() {
        assertTrue(transition.isValid(IncidentState.NEW, IncidentState.IN_PROGRESS));
    }

    @Test
    @DisplayName("NEW can transition to ON_HOLD")
    void newToOnHold() {
        assertTrue(transition.isValid(IncidentState.NEW, IncidentState.ON_HOLD));
    }

    @Test
    @DisplayName("NEW cannot transition directly to RESOLVED")
    void newToResolved() {
        assertFalse(transition.isValid(IncidentState.NEW, IncidentState.RESOLVED));
    }

    @Test
    @DisplayName("NEW cannot transition directly to CLOSED")
    void newToClosed() {
        assertFalse(transition.isValid(IncidentState.NEW, IncidentState.CLOSED));
    }

    @Test
    @DisplayName("IN_PROGRESS can transition to RESOLVED")
    void inProgressToResolved() {
        assertTrue(transition.isValid(IncidentState.IN_PROGRESS, IncidentState.RESOLVED));
    }

    @Test
    @DisplayName("RESOLVED can transition to CLOSED")
    void resolvedToClosed() {
        assertTrue(transition.isValid(IncidentState.RESOLVED, IncidentState.CLOSED));
    }

    @Test
    @DisplayName("RESOLVED can reopen to IN_PROGRESS")
    void resolvedToInProgress() {
        assertTrue(transition.isValid(IncidentState.RESOLVED, IncidentState.IN_PROGRESS));
    }

    @Test
    @DisplayName("CLOSED is a terminal state")
    void closedIsTerminal() {
        IncidentState[] allowed = transition.getAllowedTransitions(IncidentState.CLOSED);
        assertEquals(0, allowed.length);
    }

    @Test
    @DisplayName("CANCELLED is a terminal state")
    void cancelledIsTerminal() {
        IncidentState[] allowed = transition.getAllowedTransitions(IncidentState.CANCELLED);
        assertEquals(0, allowed.length);
    }

    @Test
    @DisplayName("getAllowedTransitions returns correct states for NEW")
    void allowedTransitionsFromNew() {
        IncidentState[] allowed = transition.getAllowedTransitions(IncidentState.NEW);
        assertEquals(3, allowed.length);
    }
}
