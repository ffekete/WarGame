package com.mygdx.wargame.battle.unit.action;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.mygdx.wargame.battle.unit.Direction;
import com.mygdx.wargame.battle.unit.State;
import com.mygdx.wargame.mech.Mech;
import com.mygdx.wargame.util.MathUtils;

public class AttackAnimationAction extends Action {

    private Mech attackerMech;
    private Mech defenderMech;
    private double counter = 0;
    private int duration = 0;
    private int minRange;
    private int length = 0;

    public AttackAnimationAction(Mech attackerMech, Mech defenderMech, int minRange) {
        this.minRange = minRange;
        this.attackerMech = attackerMech;
        this.defenderMech = defenderMech;
        length = attackerMech.getSelectedWeapons().stream()
                .map(weapon -> weapon.getDamageMultiplier())
                .reduce((a, b) -> a + b).get();
    }

    @Override
    public boolean act(float delta) {
        if (MathUtils.getDistance(attackerMech.getX(), attackerMech.getY(), defenderMech.getX(), defenderMech.getY()) > minRange) {
            // not in range, can't do anything
            return true;
        }

        counter += delta;

        attackerMech.setState(State.Attack);

        if (counter > 0.25f) {
            duration++;
            counter = 0.0f;
        }

        if (duration >= length && duration > 1) {
            attackerMech.setState(State.Idle);
            return true;
        }
        return false;
    }
}
