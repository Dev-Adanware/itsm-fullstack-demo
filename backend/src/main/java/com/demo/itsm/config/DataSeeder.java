package com.demo.itsm.config;

import com.demo.itsm.model.Incident;
import com.demo.itsm.model.IncidentState;
import com.demo.itsm.model.Priority;
import com.demo.itsm.repository.IncidentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Seeds the database with sample incidents on startup.
 */
@Component
public class DataSeeder implements CommandLineRunner {

    private final IncidentRepository repository;

    public DataSeeder(IncidentRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {
        if (repository.count() > 0) {
            return;
        }

        createIncident("INC0000001", "Email service is down", "Users cannot send or receive emails. Exchange server is unresponsive.",
                Priority.CRITICAL, IncidentState.IN_PROGRESS, "Network", "Email", "John Smith", "IT Infrastructure", "Jane Doe");

        createIncident("INC0000002", "VPN connection timeout", "Remote users experiencing frequent VPN disconnections.",
                Priority.HIGH, IncidentState.NEW, "Network", "VPN", "Alice Johnson", "Network Team", null);

        createIncident("INC0000003", "Printer not working on 3rd floor", "HP LaserJet on 3rd floor shows offline status.",
                Priority.LOW, IncidentState.NEW, "Hardware", "Printer", "Bob Wilson", "Desktop Support", null);

        createIncident("INC0000004", "SAP login failure", "Multiple users unable to authenticate to SAP system.",
                Priority.HIGH, IncidentState.IN_PROGRESS, "Software", "SAP", "Carol Davis", "Application Support", "Mike Brown");

        createIncident("INC0000005", "Database performance degradation", "Production database queries taking 10x longer than normal.",
                Priority.CRITICAL, IncidentState.IN_PROGRESS, "Software", "Database", "David Lee", "DBA Team", "Sarah Chen");

        createIncident("INC0000006", "New laptop setup request", "New employee needs laptop configured with standard software.",
                Priority.PLANNING, IncidentState.NEW, "Hardware", "Laptop", "Eva Martinez", "Desktop Support", null);

        createIncident("INC0000007", "Website SSL certificate expiring", "SSL cert for company website expires in 7 days.",
                Priority.MODERATE, IncidentState.ON_HOLD, "Network", "Security", "Frank Taylor", "Security Team", "Grace Kim");

        createIncident("INC0000008", "Outlook crashes on startup", "Outlook 365 crashes immediately after launching for several users.",
                Priority.MODERATE, IncidentState.RESOLVED, "Software", "Email", "Helen White", "Desktop Support", "Tom Harris");

        createIncident("INC0000009", "Badge access not working", "Employee badge denied at building B entrance after system update.",
                Priority.HIGH, IncidentState.NEW, "Hardware", "Access Control", "Ian Clarke", "Facilities", null);

        createIncident("INC0000010", "Shared drive permissions error", "Marketing team unable to access shared drive after migration.",
                Priority.MODERATE, IncidentState.IN_PROGRESS, "Software", "File Storage", "Julia Park", "IT Infrastructure", "Kevin Ross");
    }

    private void createIncident(String number, String shortDesc, String desc,
                                 Priority priority, IncidentState state, String category,
                                 String subcategory, String caller, String group, String assignedTo) {
        Incident incident = new Incident();
        incident.setNumber(number);
        incident.setShortDescription(shortDesc);
        incident.setDescription(desc);
        incident.setPriority(priority);
        incident.setState(state);
        incident.setCategory(category);
        incident.setSubcategory(subcategory);
        incident.setCaller(caller);
        incident.setAssignmentGroup(group);
        incident.setCreatedBy("system");
        incident.setUpdatedBy("system");
        if (assignedTo != null) {
            incident.assignTo(assignedTo);
        }
        repository.save(incident);
    }
}
