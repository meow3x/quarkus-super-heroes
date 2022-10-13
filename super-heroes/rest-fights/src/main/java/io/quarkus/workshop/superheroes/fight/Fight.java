package io.quarkus.workshop.superheroes.fight;

import java.time.Instant;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
public class Fight extends PanacheEntity {
    @NotNull
    public Instant fightDate;
    @NotNull
    public String winnerName;
    @NotNull
    public int winnerLevel;
    @NotNull
    public String winnerPicture;
    @NotNull
    public String loserName;
    @NotNull
    public int loserLevel;
    @NotNull
    public String loserPicture;
    @NotNull
    public String winnerTeam;
    @NotNull
    public String loserTeam;
}
