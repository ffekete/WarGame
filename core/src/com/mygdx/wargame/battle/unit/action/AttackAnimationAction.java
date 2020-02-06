package com.mygdx.wargame.battle.unit.action;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.battle.unit.State;
import com.mygdx.wargame.mech.Mech;
import com.mygdx.wargame.pilot.Pilot;
import com.mygdx.wargame.rules.calculator.RangeCalculator;
import com.mygdx.wargame.util.MathUtils;

import java.util.Comparator;

public class AttackAnimationAction extends Action {

    private Mech attackerMech;
    private Mech defenderMech;
    private double counter = 0;
    private int duration = 0;
    private int minRange;
    private Pilot pilot;

    public AttackAnimationAction(Mech attackerMech, Mech defenderMech, int minRange, Pilot attackerPilot) {
        this.minRange = minRange;
        this.attackerMech = attackerMech;
        this.defenderMech = defenderMech;
        this.pilot = attackerPilot;
    }

    @Override
    public boolean act(float delta) {

        if(MathUtils.getDistance(attackerMech.getX(), attackerMech.getY(), defenderMech.getX(), defenderMech.getY()) > minRange) {
            // not in range, can't do anything
            return true;
        }

        counter += delta;

        if (counter > 0.15f) {
            duration++;
            counter = 0.0f;
            attackerMech.setState(State.Attack);
        }

        if(duration == 10) {
            attackerMech.setState(State.Idle);
            return true;
        }
        return false;
    }
}
