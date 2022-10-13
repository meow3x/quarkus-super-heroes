package io.quarkus.workshop.superheroes.fight.client;

import javax.validation.constraints.NotNull;

public class Villain {
    @NotNull
    public String name;
    @NotNull
    public int level;
    @NotNull
    public String picture;
    public String powers;
}
