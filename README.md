# ServiceNow ITSM - Incident Management System

A containerized full-stack demo application showcasing **Object-Oriented Programming principles** through a ServiceNow-style Incident Management System.

## Tech Stack

| Layer | Technology |
|-------|-----------|
| **Backend** | Java 17, Spring Boot 3.2, Spring Data JPA |
| **Database** | SQLite (via sqlite-jdbc + Hibernate Community Dialects) |
| **API Docs** | Swagger / OpenAPI 3 (springdoc-openapi) |
| **Frontend** | React 18, Vite, React Router, Plain CSS |
| **Containerization** | Docker, Docker Compose |

## Quick Start

### With Docker (Recommended)
```bash
docker-compose up --build
```
- **Frontend**: http://localhost:3000
- **Backend API**: http://localhost:8080/api/incidents
- **Swagger UI**: http://localhost:8080/swagger-ui.html

### Without Docker

**Backend:**
```bash
cd backend
mvn spring-boot:run
```

**Frontend:**
```bash
cd frontend
npm install
npm run dev
```

## OOP Principles Demonstrated

### 1. Abstraction
Abstract classes hide implementation details and expose only essential features.

| Class | Purpose |
|-------|---------|
| `BaseEntity` | Abstract base with `id`, timestamps, and abstract `getDisplayName()` |
| `Ticket` | Abstract ticket with common fields and abstract `getNumberPrefix()`, `getSlaHours()` |
| `TicketService<T>` | Abstract service with template method for ticket operations |

**Key code**: `BaseEntity.java`, `Ticket.java`, `TicketService.java`

### 2. Inheritance
Class hierarchy enables code reuse and specialization.

```
BaseEntity (abstract)
  └── Ticket (abstract)
        ├── Incident        → prefix: INC, SLA by priority
        └── ChangeRequest   → prefix: CHG, SLA by change type

TicketService<T> (abstract)
  └── IncidentService   → concrete implementation with incident-specific logic
```

### 3. Polymorphism
Same interface, different behavior depending on the concrete type.

| Type | Example |
|------|---------|
| **Interface Polymorphism** | `Assignable` and `Auditable` interfaces implemented by different ticket types |
| **Method Overriding** | `getSlaHours()` returns different values for `Incident` vs `ChangeRequest` |
| **Strategy Pattern** | `StateTransition<S>` interface with `IncidentStateTransition` implementation |
| **Template Method** | `TicketService.create()` defines algorithm, subclasses customize via hooks |

### 4. Encapsulation
Data hiding and controlled access to internal state.

| Technique | Where |
|-----------|-------|
| **Private fields** | All entity fields are private with getters/setters |
| **DTOs** | `IncidentRequest` / `IncidentResponse` control API contract separately from domain model |
| **Builder Pattern** | `IncidentRequest.Builder` for fluent, controlled object construction |

## Design Patterns Used

| Pattern | Location | Purpose |
|---------|----------|---------|
| **Strategy** | `StateTransition` / `IncidentStateTransition` | Encapsulate state transition rules |
| **Template Method** | `TicketService.create()` | Define algorithm skeleton, let subclasses customize |
| **Builder** | `IncidentRequest.Builder` | Clean construction of objects with many optional fields |
| **Factory Method** | `IncidentResponse.fromEntity()` | Convert domain entity to DTO |
| **Repository** | `IncidentRepository` | Abstract data access layer |

## REST API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/incidents` | List all incidents (filter by `?state=` or `?priority=`) |
| `GET` | `/api/incidents/{id}` | Get incident by ID |
| `POST` | `/api/incidents` | Create new incident |
| `PUT` | `/api/incidents/{id}` | Update incident |
| `DELETE` | `/api/incidents/{id}` | Delete incident |
| `PATCH` | `/api/incidents/{id}/state?newState=` | Transition incident state |
| `PATCH` | `/api/incidents/{id}/resolve` | Resolve with notes |
| `GET` | `/api/incidents/{id}/transitions` | Get allowed state transitions |
| `GET` | `/api/incidents/statistics` | Dashboard statistics |

## Incident State Machine

```
NEW → IN_PROGRESS → RESOLVED → CLOSED
 │        │            │
 │        ├→ ON_HOLD ──┘ (back to IN_PROGRESS)
 │        │
 │        └→ CANCELLED
 └→ ON_HOLD
 └→ CANCELLED
```

## Unit Tests

```bash
cd backend
mvn test
```

Tests cover:
- **Model tests**: Inheritance, polymorphism, encapsulation verification
- **Service tests**: Template method pattern, state transition strategy
- **State transition tests**: Valid/invalid transition rules

## Project Structure

```
servicenow_itsm/
├── backend/
│   ├── src/main/java/com/demo/itsm/
│   │   ├── model/          # Domain model (OOP showcase)
│   │   ├── dto/            # Data Transfer Objects (Encapsulation)
│   │   ├── repository/     # Data access layer
│   │   ├── service/        # Business logic (Design Patterns)
│   │   ├── controller/     # REST API
│   │   └── config/         # Swagger, CORS, Data Seeder
│   ├── src/test/java/      # Unit tests
│   ├── Dockerfile
│   └── pom.xml
├── frontend/
│   ├── src/
│   │   ├── components/     # React components
│   │   ├── services/       # API client
│   │   ├── App.jsx         # Main app with routing
│   │   └── App.css         # Styles
│   ├── Dockerfile
│   └── nginx.conf
├── docker-compose.yml
└── README.md
```
