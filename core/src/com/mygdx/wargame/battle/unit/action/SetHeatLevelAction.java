package com.mygdx.wargame.battle.unit.action;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.mygdx.wargame.common.mech.Mech;

public class SetHeatLevelAction extends Action {

    private Mech targetMech;
    private int amount;

    public SetHeatLevelAction(Mech targetMech, int amount) {
        this.targetMech = targetMech;
        this.amount = amount;
    }

    @Override
    public boolean act(float delta) {
        targetMech.setHeatLevel(targetMech.getHeatLevel() + amount);
        return true;
    }
}
