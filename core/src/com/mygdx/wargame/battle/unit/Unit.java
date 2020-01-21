package com.mygdx.wargame.battle.unit;

import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.HashSet;
import java.util.Set;

public class Unit extends Actor {

    private Set<AbstractWarrior> entities;
    private Team team;

    public Unit() {
        this.entities = new HashSet<>();
    }

    public void add(AbstractWarrior man) {
        this.entities.add(man);
    }

    public Set<AbstractWarrior> getAll() {
        return entities;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
