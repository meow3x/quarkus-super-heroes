package io.quarkus.workshop.superheroes.villain;

import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
public class Villain extends PanacheEntity {
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

    @Override
    public String toString() {
        return "Villain{id=" + id + ", name='" + name + "'}";
    }

    public static Villain findRandom() {
        Random random = new Random();
        final int index = random.nextInt((int) count());
        return findAll().page(index, 1).firstResult();
    }
}
