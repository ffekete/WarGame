package com.mygdx.wargame.battle.unit;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class AbstractWarrior extends Actor implements Man {

    private Unit unit;

    @Override
    public int getHp() {
        return 1;
    }

    @Override
    public void setHp(int hp) {

    }

    @Override
    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    @Override
    public Unit getUnit() {
        return unit;
    }
}
