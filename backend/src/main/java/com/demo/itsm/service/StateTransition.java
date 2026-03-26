package com.demo.itsm.service;

/**
 * OOP: POLYMORPHISM + DESIGN PATTERN (Strategy Pattern)
 * Generic interface for state transition logic.
 * Different ticket types provide their own transition rules.
 *
 * @param <S> The state enum type
 */
public interface StateTransition<S> {

    /**
     * Check if transitioning from one state to another is allowed.
     */
    boolean isValid(S from, S to);

    /**
     * Get all states that can be transitioned to from the given state.
     */
    S[] getAllowedTransitions(S from);
}
