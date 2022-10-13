package io.quarkus.workshop.superheroes.hero;

import java.net.URI;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.RestPath;

import io.quarkus.hibernate.reactive.panache.common.runtime.ReactiveTransactional;
import io.smallrye.mutiny.Uni;

@Path("/api/heroes")
public class HeroResource {
    private final Logger logger = Logger.getLogger(HeroResource.class);

    @Operation(summary = "Get a random hero")
    @GET
    @Path("/random")
    @APIResponse(responseCode = "200",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = Hero.class)))
    public Uni<Hero> getRandomHero() {
        return Hero.findRandom()
            .invoke(h -> logger.info("Fetched: " + h));
    }

    @GET
    public Uni<List<Hero>> findAll() {
        return Hero.findAll().list();
    }

    @GET
    @Path("/{id}")
    public Uni<Response> findById(@RestPath Long id) {
        return Hero.<Hero>findById(id)
            .map(hero -> Objects.isNull(hero)
                ? Response.status(Response.Status.NOT_FOUND).build()
                : Response.ok(hero).build()
            );
    }

    @POST
    @ReactiveTransactional
    public Uni<Response> create(@Valid Hero requestBody, @Context UriInfo uriInfo) {
        return requestBody.<Hero>persist()
            .map(hero -> {
                final URI location = uriInfo.getAbsolutePathBuilder()
                    .path(String.valueOf(hero.id))
                    .build();
                return Response.created(location).build();
            });
    }

    // update

    @DELETE
    @Path("/{id}")
    @ReactiveTransactional
    public Uni<Response> delete(@RestPath Long id) {
        return Hero.deleteById(id)
            .replaceWith(Response.noContent().build());
    }
}