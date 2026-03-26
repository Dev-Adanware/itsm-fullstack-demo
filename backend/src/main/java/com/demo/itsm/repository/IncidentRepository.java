package com.demo.itsm.repository;

import com.demo.itsm.model.Incident;
import com.demo.itsm.model.IncidentState;
import com.demo.itsm.model.Priority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IncidentRepository extends JpaRepository<Incident, Long> {

    Optional<Incident> findByNumber(String number);

    List<Incident> findByState(IncidentState state);

    List<Incident> findByPriority(Priority priority);

    List<Incident> findByAssignedTo(String assignedTo);

    List<Incident> findByCategory(String category);

    long countByState(IncidentState state);

    long countByPriority(Priority priority);
}
