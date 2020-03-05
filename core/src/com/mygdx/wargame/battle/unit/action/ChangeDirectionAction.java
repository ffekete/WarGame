package com.mygdx.wargame.battle.unit.action;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.mygdx.wargame.battle.unit.Direction;
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
            } else {
                target.setDirection(Direction.Right);
            }
        } else {
            if (ty < target.getY()) {
                target.setDirection(Direction.Down);
            } else {
                target.setDirection(Direction.Up);
            }
        }

        return true;
    }
}
