package com.mygdx.wargame.battle.unit.action;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.mygdx.wargame.battle.unit.Direction;
import com.mygdx.wargame.battle.unit.State;
import com.mygdx.wargame.common.mech.Mech;

public class ChangeDirectionAction extends Action {

    private float tx, ty;
    private Mech target;

    public ChangeDirectionAction(float tx, float ty, Mech target) {
        this.tx = tx;
        this.ty = ty;
        this.target = target;
    }

    @Override
    public boolean act(float delta) {
        float dx = Math.abs(tx - target.getX());
        float dy = Math.abs(ty - target.getY());

        if (dx >= dy) {
            if (tx < target.getX()) {
                target.setDirection(Direction.Left);
                target.setState(State.WalkLeft);
            } else {
                target.setDirection(Direction.Right);
                target.setState(State.WalkRight);
            }
        } else {
            if (ty < target.getY()) {
                target.setDirection(Direction.Down);
                target.setState(State.WalkDown);
            } else {
                target.setDirection(Direction.Up);
                target.setState(State.WalkUp);
            }
        }

        return true;
    }
}
