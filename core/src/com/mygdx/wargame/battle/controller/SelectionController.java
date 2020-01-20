package com.mygdx.wargame.battle.controller;

import com.mygdx.wargame.battle.unit.Man;
import com.mygdx.wargame.battle.unit.Unit;

import java.util.Set;

public class SelectionController {

    private Set<Man> selected;

    public SelectionController(Set<Man> selected) {
        this.selected = selected;
    }

    public boolean isSelected(Man man) {
        return selected.contains(man);
    }

    public void select(Man man) {
        this.selected.add(man);
    }

    public void deselect(Man man) {
        this.selected.remove(man);
    }

    public void selectUnit(Unit unit) {
        unit.getAll().forEach(this::select);
    }

    public void deselectUnit(Unit unit) {
        unit.getAll().forEach(this::deselect);
    }

    public void deselectAll() {
        selected.clear();
    }
}
