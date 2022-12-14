package io.quarkus.workshop.superheroes.hero;

import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import io.smallrye.mutiny.Uni;

@Entity
public class Hero extends PanacheEntity {
    @NotNull
    @Size(min = 3, max = 50)
    public String name;
    public String otherName;

    @NotNull
    @Min(1)
    public int level;
    public String picture;

    @Column(columnDefinition = "text")
    public String powers;

    public static Uni<Hero> findRandom() {
        var random = new Random();
        return count()
            .map(count -> random.nextInt(count.intValue()))
            .chain(randomHero -> findAll().page(randomHero, 1).firstResult());
    }

    @Override
    public String toString() {
        return "Hero{name='" + name + "'}";
    }
}
