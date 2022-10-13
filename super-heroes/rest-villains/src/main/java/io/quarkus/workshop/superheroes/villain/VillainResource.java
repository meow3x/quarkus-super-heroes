package io.quarkus.workshop.superheroes.villain;

import java.net.URI;

import javax.validation.Valid;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.headers.Header;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.RestPath;

@Path("/api/villains")
@Tag(name = "villains")
public class VillainResource {
    final private Logger logger;// = Logger.getLogger(VillainResource.class);
    final private VillainService service;

    public VillainResource(Logger logger, VillainService service) {
        this.logger = logger;
        this.service = service;
    }

    @Operation(summary = "Retreive a random villain")
    @GET
    @Path("/random")
    @APIResponse(responseCode = "200",
        content = {
            @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Villain.class))
        })
    public Response findRandom() {
        var random = service.findRandom();
        logger.info("Found: " + random);
        return Response.ok(random).build();
    }

    @Operation(summary = "Retrieve all villains")
    @GET
    @APIResponse(
        responseCode = "200",
        content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Villain.class, type = SchemaType.ARRAY))
    )
    public Response findAll() {
        return Response.ok(service.findVillains()).build();
    }

    @Operation(summary = "Retrieve villain by id")
    @GET
    @Path("/{id}")
    @APIResponse(
        responseCode = "200",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON,
            schema = @Schema(implementation = Villain.class)
        )
    )
    @APIResponse(
        responseCode = "404",
        description = "Cannot find villain with id"
    )
    public Response find(@RestPath Long id) {
        var entity = service.findVillainById(id);
        if(entity == null) {
            return Response.status(Status.NOT_FOUND).build();
        }
        return Response.ok(entity).build();
    }

    @Operation(summary = "Create a villain")
    @POST
    @APIResponse(
        responseCode = "201",
        description = "Created",
        headers = @Header(
            name = "Location",
            description = "Location of newly created villain",
            schema = @Schema(implementation = URI.class)
        )
    )
    @APIResponse(
        responseCode = "500",
        description = "Invalid fields detected"
    )
    public Response create(@Valid Villain villain, @Context UriInfo uriInfo) {
        logger.info("VillainResource@create");
        villain = service.persistVillain(villain);
        return Response
            .created(uriInfo.getAbsolutePathBuilder().path(String.valueOf(villain.id)).build())
            .build();
    }

    @PUT
    public Response update(@Valid Villain villain) {
        villain = service.updateVillain(villain);
        return Response.ok(villain).build();
    }

    @DELETE
    @Path("/{id}")
    public void delete(@RestPath Long id) {
        service.deleteVillain(id);
    }
}