package com.demo.itsm.service;

import com.demo.itsm.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

/**
 * OOP: ABSTRACTION + DESIGN PATTERN (Template Method Pattern)
 * Abstract service defining the skeleton of ticket operations.
 * Subclasses implement the specifics (number generation, validation, etc.).
 *
 * @param <T> The ticket entity type
 */
public abstract class TicketService<T extends Ticket> {

    private final AtomicLong counter = new AtomicLong(0);

    protected abstract JpaRepository<T, Long> getRepository();

    /**
     * Template method: generate the next ticket number.
     * Uses the subclass's prefix (INC, CHG, etc.)
     */
    protected String generateNumber(T ticket) {
        long count = getRepository().count() + counter.incrementAndGet();
        return String.format("%s%07d", ticket.getNumberPrefix(), count);
    }

    /**
     * Template method: hook for subclass-specific validation before save.
     */
    protected abstract void validateBeforeSave(T ticket);

    /**
     * Template method: create a new ticket.
     * Defines the algorithm skeleton; subclasses customize via hooks.
     */
    public T create(T ticket) {
        ticket.setNumber(generateNumber(ticket));
        ticket.setCreatedBy("system");
        ticket.setUpdatedBy("system");
        validateBeforeSave(ticket);
        return getRepository().save(ticket);
    }

    public List<T> findAll() {
        return getRepository().findAll();
    }

    public Optional<T> findById(Long id) {
        return getRepository().findById(id);
    }

    public T update(T ticket) {
        ticket.setUpdatedBy("system");
        validateBeforeSave(ticket);
        return getRepository().save(ticket);
    }

    public void delete(Long id) {
        getRepository().deleteById(id);
    }
}
