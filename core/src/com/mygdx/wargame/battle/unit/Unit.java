package com.mygdx.wargame.battle.unit;

import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.HashSet;
import java.util.Set;

public class Unit extends Actor {

    private Set<Man> entities;

    public Unit() {
        this.entities = new HashSet<>();
    }

    public void add(Man man) {
        this.entities.add(man);
    }

    public Set<Man> getAll() {
        return entities;
    }
}
