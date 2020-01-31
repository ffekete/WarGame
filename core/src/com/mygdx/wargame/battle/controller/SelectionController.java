package com.mygdx.wargame.battle.controller;

import com.mygdx.wargame.mech.AbstractMech;

public class SelectionController {

    private AbstractMech selected;

    public SelectionController(AbstractMech selected) {
        this.selected = selected;
    }

    public boolean isSelected(AbstractMech man) {
        if(selected == null)
            return false;
        return selected.equals(man);
    }

    public void select(AbstractMech man) {
        this.selected = man;
    }

    public AbstractMech getSelected() {
        return this.selected;
    }

    public void deselect() {
        this.selected = null;
    }


}
