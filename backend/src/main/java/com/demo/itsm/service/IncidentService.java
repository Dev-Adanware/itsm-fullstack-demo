package com.demo.itsm.service;

import com.demo.itsm.dto.IncidentRequest;
import com.demo.itsm.dto.IncidentResponse;
import com.demo.itsm.model.Incident;
import com.demo.itsm.model.IncidentState;
import com.demo.itsm.model.Priority;
import com.demo.itsm.repository.IncidentRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * OOP: INHERITANCE (extends abstract TicketService)
 * Concrete service for Incident management.
 * Implements abstract hooks and adds incident-specific operations.
 */
@Service
public class IncidentService extends TicketService<Incident> {

    private final IncidentRepository repository;
    private final IncidentStateTransition stateTransition;

    public IncidentService(IncidentRepository repository, IncidentStateTransition stateTransition) {
        this.repository = repository;
        this.stateTransition = stateTransition;
    }

    @Override
    protected JpaRepository<Incident, Long> getRepository() {
        return repository;
    }

    /**
     * OOP: INHERITANCE - Implementing abstract hook from TicketService.
     */
    @Override
    protected void validateBeforeSave(Incident incident) {
        if (incident.getShortDescription() == null || incident.getShortDescription().isBlank()) {
            throw new IllegalArgumentException("Short description is required");
        }
    }

    /**
     * Create incident from DTO - demonstrates encapsulation of mapping logic.
     */
    public IncidentResponse createFromRequest(IncidentRequest request) {
        Incident incident = new Incident();
        mapRequestToEntity(request, incident);
        Incident saved = create(incident);
        return IncidentResponse.fromEntity(saved);
    }

    /**
     * Update incident from DTO.
     */
    public IncidentResponse updateFromRequest(Long id, IncidentRequest request) {
        Incident incident = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Incident not found: " + id));
        mapRequestToEntity(request, incident);
        Incident saved = update(incident);
        return IncidentResponse.fromEntity(saved);
    }

    /**
     * Transition incident state using Strategy pattern.
     */
    public IncidentResponse transitionState(Long id, IncidentState newState) {
        Incident incident = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Incident not found: " + id));

        if (!stateTransition.isValid(incident.getState(), newState)) {
            throw new IllegalStateException(
                    String.format("Cannot transition from %s to %s", incident.getState(), newState)
            );
        }

        incident.setState(newState);
        Incident saved = update(incident);
        return IncidentResponse.fromEntity(saved);
    }

    /**
     * Resolve an incident with notes.
     */
    public IncidentResponse resolve(Long id, String resolutionNotes) {
        Incident incident = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Incident not found: " + id));

        if (!stateTransition.isValid(incident.getState(), IncidentState.RESOLVED)) {
            throw new IllegalStateException("Cannot resolve incident in state: " + incident.getState());
        }

        incident.setState(IncidentState.RESOLVED);
        incident.setResolutionNotes(resolutionNotes);
        Incident saved = update(incident);
        return IncidentResponse.fromEntity(saved);
    }

    public List<IncidentResponse> findAllResponses() {
        return findAll().stream().map(IncidentResponse::fromEntity).toList();
    }

    public IncidentResponse findResponseById(Long id) {
        return findById(id).map(IncidentResponse::fromEntity)
                .orElseThrow(() -> new IllegalArgumentException("Incident not found: " + id));
    }

    public List<IncidentResponse> findByState(IncidentState state) {
        return repository.findByState(state).stream().map(IncidentResponse::fromEntity).toList();
    }

    public List<IncidentResponse> findByPriority(Priority priority) {
        return repository.findByPriority(priority).stream().map(IncidentResponse::fromEntity).toList();
    }

    public IncidentState[] getAllowedTransitions(Long id) {
        Incident incident = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Incident not found: " + id));
        return stateTransition.getAllowedTransitions(incident.getState());
    }

    /**
     * Dashboard statistics.
     */
    public Map<String, Object> getStatistics() {
        return Map.of(
                "total", repository.count(),
                "new", repository.countByState(IncidentState.NEW),
                "inProgress", repository.countByState(IncidentState.IN_PROGRESS),
                "onHold", repository.countByState(IncidentState.ON_HOLD),
                "resolved", repository.countByState(IncidentState.RESOLVED),
                "closed", repository.countByState(IncidentState.CLOSED),
                "critical", repository.countByPriority(Priority.CRITICAL),
                "high", repository.countByPriority(Priority.HIGH)
        );
    }

    // --- Private helper ---

    private void mapRequestToEntity(IncidentRequest request, Incident incident) {
        incident.setShortDescription(request.getShortDescription());
        incident.setDescription(request.getDescription());
        incident.setPriority(request.getPriority() != null ? request.getPriority() : Priority.MODERATE);
        incident.setCategory(request.getCategory());
        incident.setSubcategory(request.getSubcategory());
        incident.setCaller(request.getCaller());
        incident.setImpact(request.getImpact());
        incident.setUrgency(request.getUrgency());
        if (request.getAssignedTo() != null) {
            incident.assignTo(request.getAssignedTo());
        }
        if (request.getAssignmentGroup() != null) {
            incident.setAssignmentGroup(request.getAssignmentGroup());
        }
    }
}
