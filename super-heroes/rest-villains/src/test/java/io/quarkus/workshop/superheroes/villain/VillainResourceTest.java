package io.quarkus.workshop.superheroes.villain;

import static io.restassured.RestAssured.given;
import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.OK;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class VillainResourceTest {
    @Test
    void unknownVillainShouldReturn404() {
        given()
            .pathParam("id", Long.MAX_VALUE) // Effectively invalid
        .when()
            .get("/api/villains/{id}")
        .then()
            .statusCode(NOT_FOUND.getStatusCode());

    }

    @Test
    void shouldRejectInvalidVillain() {
        Villain villain = new Villain();
        villain.name = null; // error
        villain.otherName = "<name>";
        villain.picture = "picture";
        villain.powers = "powers";
        villain.level = 0; // error

        given()
            .body(villain)
            .contentType(ContentType.JSON)
            // .accept(ContentType.JSON)
        .when()
            .post("/api/villains")
        .then()
            .statusCode(INTERNAL_SERVER_ERROR.getStatusCode()); // BAD_REQUEST
    }

    @Test
    void shouldCreateValidVillain() {
        Villain villain = new Villain();
        villain.name = "test villain"; // [3,50]
        villain.otherName = "name";
        villain.picture = "picture";
        villain.powers = "powers";
        villain.level = 5; // ! note scaling in VillainService

        final String location = given()
            .body(villain)
            .contentType("application/json;charset=UTF-8")
            // .accept(ContentType.JSON)
        .when()
            .post("/api/villains")
        .then()
            .statusCode(CREATED.getStatusCode())
        .extract()
            .header("Location");

        assertTrue(location.contains("/api/villains/"));

        // Validate if saved
        given().when().get(location)
        .then()
            .statusCode(OK.getStatusCode())
            .contentType(ContentType.JSON)
            .body("name", is(villain.name))
            .body("otherName", is(villain.otherName))
            .body("level", is(villain.level))
            .body("picture", is(villain.picture))
            .body("powers", is(villain.powers));
    }
}