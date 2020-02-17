package com.mygdx.wargame.battle.unit.action;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.mygdx.wargame.battle.unit.State;
import com.mygdx.wargame.mech.Mech;

public class SetStateAction extends Action {

    private Mech mech;
    private State state;

    public SetStateAction(Mech mech, State state) {
        this.mech = mech;
        this.state = state;
    }

    @Override
    public boolean act(float delta) {
        this.mech.setState(state);
        return true;
    }
}
