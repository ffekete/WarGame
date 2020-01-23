package com.mygdx.wargame.battle.controller;

import com.mygdx.wargame.battle.unit.AbstractWarrior;
import com.mygdx.wargame.battle.unit.Man;

import java.util.Set;

public class SelectionController {

    private AbstractWarrior selected;

    public SelectionController(AbstractWarrior selected) {
        this.selected = selected;
    }

    public boolean isSelected(AbstractWarrior man) {
        if(selected == null)
            return false;
        return selected.equals(man);
    }

    public void select(AbstractWarrior man) {
        this.selected = man;
    }

    public AbstractWarrior getSelected() {
        return this.selected;
    }

    public void deselect() {
        this.selected = null;
    }


}
