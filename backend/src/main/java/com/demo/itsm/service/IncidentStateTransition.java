package com.demo.itsm.service;

import com.demo.itsm.model.IncidentState;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * OOP: POLYMORPHISM (Strategy Pattern implementation)
 * Concrete strategy that defines valid incident state transitions.
 * Follows ServiceNow's incident lifecycle rules.
 */
@Component
public class IncidentStateTransition implements StateTransition<IncidentState> {

    private static final Map<IncidentState, Set<IncidentState>> TRANSITIONS = new EnumMap<>(IncidentState.class);

    static {
        TRANSITIONS.put(IncidentState.NEW, EnumSet.of(
                IncidentState.IN_PROGRESS, IncidentState.ON_HOLD, IncidentState.CANCELLED
        ));
        TRANSITIONS.put(IncidentState.IN_PROGRESS, EnumSet.of(
                IncidentState.ON_HOLD, IncidentState.RESOLVED, IncidentState.CANCELLED
        ));
        TRANSITIONS.put(IncidentState.ON_HOLD, EnumSet.of(
                IncidentState.IN_PROGRESS, IncidentState.CANCELLED
        ));
        TRANSITIONS.put(IncidentState.RESOLVED, EnumSet.of(
                IncidentState.IN_PROGRESS, IncidentState.CLOSED
        ));
        TRANSITIONS.put(IncidentState.CLOSED, EnumSet.noneOf(IncidentState.class));
        TRANSITIONS.put(IncidentState.CANCELLED, EnumSet.noneOf(IncidentState.class));
    }

    @Override
    public boolean isValid(IncidentState from, IncidentState to) {
        Set<IncidentState> allowed = TRANSITIONS.getOrDefault(from, EnumSet.noneOf(IncidentState.class));
        return allowed.contains(to);
    }

    @Override
    public IncidentState[] getAllowedTransitions(IncidentState from) {
        Set<IncidentState> allowed = TRANSITIONS.getOrDefault(from, EnumSet.noneOf(IncidentState.class));
        return allowed.toArray(new IncidentState[0]);
    }
}
