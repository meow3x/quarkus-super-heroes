package io.quarkus.workshop.superheroes.fight;

import javax.validation.constraints.NotNull;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import io.quarkus.workshop.superheroes.fight.client.Hero;
import io.quarkus.workshop.superheroes.fight.client.Villain;

@Schema(description = "Fight between a hero & villain")
public class Fighters {
    @NotNull public Hero hero;
    @NotNull public Villain villain;
}
