package com.demo.itsm.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("ServiceNow ITSM - Incident Management API")
                        .version("1.0.0")
                        .description("""
                                RESTful API for ServiceNow-style Incident Management.
                                
                                This demo showcases Object-Oriented Programming principles:
                                - **Abstraction**: Abstract base classes (BaseEntity, Ticket)
                                - **Inheritance**: Class hierarchy (Incident extends Ticket extends BaseEntity)
                                - **Polymorphism**: Interfaces (Assignable, Auditable) and Strategy pattern (StateTransition)
                                - **Encapsulation**: DTOs, private fields, controlled access
                                - **Design Patterns**: Builder, Strategy, Template Method, Factory Method
                                """)
                        .contact(new Contact()
                                .name("Demo Developer")
                                .email("developer@demo.com")));
    }
}
