package com.demo.itsm.controller;

import com.demo.itsm.dto.IncidentRequest;
import com.demo.itsm.dto.IncidentResponse;
import com.demo.itsm.model.IncidentState;
import com.demo.itsm.model.Priority;
import com.demo.itsm.service.IncidentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/incidents")
@Tag(name = "Incidents", description = "ServiceNow Incident Management endpoints")
public class IncidentController {

    private final IncidentService incidentService;

    public IncidentController(IncidentService incidentService) {
        this.incidentService = incidentService;
    }

    @GetMapping
    @Operation(summary = "List all incidents", description = "Retrieve all incidents with optional filtering by state or priority")
    public ResponseEntity<List<IncidentResponse>> getAll(
            @RequestParam(required = false) IncidentState state,
            @RequestParam(required = false) Priority priority) {

        List<IncidentResponse> incidents;
        if (state != null) {
            incidents = incidentService.findByState(state);
        } else if (priority != null) {
            incidents = incidentService.findByPriority(priority);
        } else {
            incidents = incidentService.findAllResponses();
        }
        return ResponseEntity.ok(incidents);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get incident by ID")
    public ResponseEntity<IncidentResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(incidentService.findResponseById(id));
    }

    @PostMapping
    @Operation(summary = "Create a new incident")
    public ResponseEntity<IncidentResponse> create(@Valid @RequestBody IncidentRequest request) {
        IncidentResponse created = incidentService.createFromRequest(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing incident")
    public ResponseEntity<IncidentResponse> update(@PathVariable Long id, @Valid @RequestBody IncidentRequest request) {
        return ResponseEntity.ok(incidentService.updateFromRequest(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an incident")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        incidentService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/state")
    @Operation(summary = "Transition incident state", description = "Change the incident state following allowed transition rules")
    public ResponseEntity<IncidentResponse> transitionState(
            @PathVariable Long id,
            @RequestParam IncidentState newState) {
        return ResponseEntity.ok(incidentService.transitionState(id, newState));
    }

    @PatchMapping("/{id}/resolve")
    @Operation(summary = "Resolve an incident", description = "Mark incident as resolved with resolution notes")
    public ResponseEntity<IncidentResponse> resolve(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {
        String notes = body.getOrDefault("resolutionNotes", "");
        return ResponseEntity.ok(incidentService.resolve(id, notes));
    }

    @GetMapping("/{id}/transitions")
    @Operation(summary = "Get allowed state transitions", description = "Returns the states this incident can transition to")
    public ResponseEntity<IncidentState[]> getAllowedTransitions(@PathVariable Long id) {
        return ResponseEntity.ok(incidentService.getAllowedTransitions(id));
    }

    @GetMapping("/statistics")
    @Operation(summary = "Get dashboard statistics", description = "Returns counts by state and priority")
    public ResponseEntity<Map<String, Object>> getStatistics() {
        return ResponseEntity.ok(incidentService.getStatistics());
    }
}
