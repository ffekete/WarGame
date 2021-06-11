package com.mygdx.wargame.battle.unit.action;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.mygdx.wargame.common.mech.Mech;

public class MeleeAttackAnimationAction extends TemporalAction {

    private Mech attackerMech;

    private int x, y;
    private int ox, oy;

    public MeleeAttackAnimationAction(Mech attackerMech, Mech defenderMech) {
        this.attackerMech = attackerMech;

        x = (int) (defenderMech.getX() - attackerMech.getX());
        y = (int) (defenderMech.getY() - attackerMech.getY());

        ox = (int)attackerMech.getX();
        oy = (int)attackerMech.getY();
        this.setDuration(0.25f);
    }

    @Override
    protected void update(float percent) {
        if (percent < 0.99f) {
            attackerMech.setPosition(ox + x * percent, oy + y * percent);
        } else {
            attackerMech.setPosition(ox, oy);
        }
    }
}
