package com.demo.itsm.service;

import com.demo.itsm.dto.IncidentRequest;
import com.demo.itsm.dto.IncidentResponse;
import com.demo.itsm.model.Incident;
import com.demo.itsm.model.IncidentState;
import com.demo.itsm.model.Priority;
import com.demo.itsm.repository.IncidentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("IncidentService - Template Method Pattern Tests")
class IncidentServiceTest {

    @Mock
    private IncidentRepository repository;

    @Mock
    private IncidentStateTransition stateTransition;

    @InjectMocks
    private IncidentService service;

    private Incident sampleIncident;

    @BeforeEach
    void setUp() {
        sampleIncident = new Incident();
        sampleIncident.setId(1L);
        sampleIncident.setNumber("INC0000001");
        sampleIncident.setShortDescription("Test incident");
        sampleIncident.setDescription("Test description");
        sampleIncident.setPriority(Priority.HIGH);
        sampleIncident.setState(IncidentState.NEW);
        sampleIncident.setCategory("Software");
        sampleIncident.setCreatedAt(LocalDateTime.now());
        sampleIncident.setUpdatedAt(LocalDateTime.now());
        sampleIncident.setCreatedBy("system");
        sampleIncident.setUpdatedBy("system");
    }

    @Test
    @DisplayName("Create incident from DTO request")
    void createFromRequest() {
        IncidentRequest request = IncidentRequest.builder()
                .shortDescription("New incident")
                .description("Detailed description")
                .priority(Priority.HIGH)
                .category("Network")
                .build();

        when(repository.count()).thenReturn(0L);
        when(repository.save(any(Incident.class))).thenAnswer(invocation -> {
            Incident saved = invocation.getArgument(0);
            saved.setId(1L);
            saved.setCreatedAt(LocalDateTime.now());
            saved.setUpdatedAt(LocalDateTime.now());
            return saved;
        });

        IncidentResponse response = service.createFromRequest(request);

        assertNotNull(response);
        assertEquals("New incident", response.getShortDescription());
        assertEquals(Priority.HIGH, response.getPriority());
        verify(repository).save(any(Incident.class));
    }

    @Test
    @DisplayName("Find all incidents returns list of responses")
    void findAllResponses() {
        when(repository.findAll()).thenReturn(List.of(sampleIncident));

        List<IncidentResponse> result = service.findAllResponses();

        assertEquals(1, result.size());
        assertEquals("INC0000001", result.get(0).getNumber());
    }

    @Test
    @DisplayName("State transition succeeds for valid transition")
    void transitionStateValid() {
        when(repository.findById(1L)).thenReturn(Optional.of(sampleIncident));
        when(stateTransition.isValid(IncidentState.NEW, IncidentState.IN_PROGRESS)).thenReturn(true);
        when(repository.save(any(Incident.class))).thenReturn(sampleIncident);

        IncidentResponse response = service.transitionState(1L, IncidentState.IN_PROGRESS);

        assertNotNull(response);
        verify(stateTransition).isValid(IncidentState.NEW, IncidentState.IN_PROGRESS);
    }

    @Test
    @DisplayName("State transition fails for invalid transition")
    void transitionStateInvalid() {
        when(repository.findById(1L)).thenReturn(Optional.of(sampleIncident));
        when(stateTransition.isValid(IncidentState.NEW, IncidentState.CLOSED)).thenReturn(false);

        assertThrows(IllegalStateException.class, () ->
                service.transitionState(1L, IncidentState.CLOSED)
        );
    }

    @Test
    @DisplayName("Resolve incident sets resolution notes")
    void resolveIncident() {
        sampleIncident.setState(IncidentState.IN_PROGRESS);
        when(repository.findById(1L)).thenReturn(Optional.of(sampleIncident));
        when(stateTransition.isValid(IncidentState.IN_PROGRESS, IncidentState.RESOLVED)).thenReturn(true);
        when(repository.save(any(Incident.class))).thenReturn(sampleIncident);

        IncidentResponse response = service.resolve(1L, "Fixed the issue");

        assertNotNull(response);
        verify(repository).save(any(Incident.class));
    }

    @Test
    @DisplayName("Find by ID throws when not found")
    void findByIdNotFound() {
        when(repository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () ->
                service.findResponseById(999L)
        );
    }

    @Test
    @DisplayName("Validation rejects blank short description")
    void validateRejectsBlankDescription() {
        IncidentRequest request = IncidentRequest.builder()
                .shortDescription("")
                .build();

        when(repository.count()).thenReturn(0L);

        assertThrows(IllegalArgumentException.class, () ->
                service.createFromRequest(request)
        );
    }
}
