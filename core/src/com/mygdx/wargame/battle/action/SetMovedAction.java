package com.mygdx.wargame.battle.action;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.mygdx.wargame.common.mech.AbstractMech;

public class SetMovedAction extends Action {

    private AbstractMech abstractMech;

    public SetMovedAction(AbstractMech abstractMech) {
        this.abstractMech = abstractMech;
    }


    @Override
    public boolean act(float delta) {
        abstractMech.setMoved(true);
        return true;
    }
}
