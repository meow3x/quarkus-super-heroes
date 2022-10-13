package io.quarkus.workshop.superheroes.villain;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.servers.Server;

@ApplicationPath("/")
@OpenAPIDefinition(
    info = @Info(
        title = "Villain API",
        description = "CRUD operations for villain",
        version = "1.0.0-alpha"),
    servers = {
        @Server(url = "http://localhost:8084")
    }
)
public class VillainApplication extends Application {}
