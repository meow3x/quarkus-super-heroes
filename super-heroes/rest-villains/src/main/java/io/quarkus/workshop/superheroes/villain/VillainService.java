package io.quarkus.workshop.superheroes.villain;

import static javax.transaction.Transactional.TxType.REQUIRED;
import static javax.transaction.Transactional.TxType.SUPPORTS;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
@Transactional(REQUIRED)
public class VillainService {
    @ConfigProperty(name = "level.multiplier", defaultValue = "1.0")
    double levelMultiplier;

    // crud
    @Transactional(SUPPORTS)
    public List<Villain> findVillains() {
        return Villain.listAll();
    }

    @Transactional(SUPPORTS)
    public Villain findVillainById(Long id) {
        return Villain.findById(id);
    }

    @Transactional(SUPPORTS)
    public Villain findRandom() {
        return Villain.findRandom();
    }

    public Villain persistVillain(@Valid Villain villain) {
        villain.level = (int) Math.round(levelMultiplier * villain.level);
        villain.persist();
        return villain;
    }

    public Villain updateVillain(@Valid Villain villain) {
        Villain entity = Villain.findById(villain.id);
        entity.name = villain.name;
        entity.otherName = villain.otherName;
        entity.level = villain.level;
        entity.picture = villain.picture;
        entity.powers = villain.powers;

        return entity;
    }

    public void deleteVillain(Long id) {
        Villain.deleteById(id);
    }
}
