package com.mygdx.wargame.battle.unit.action;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.mygdx.wargame.battle.unit.Direction;
import com.mygdx.wargame.battle.unit.State;
import com.mygdx.wargame.component.weapon.Weapon;
import com.mygdx.wargame.component.weapon.WeaponType;
import com.mygdx.wargame.mech.Mech;
import com.mygdx.wargame.util.MathUtils;

import java.util.ArrayList;
import java.util.List;

public class AttackAnimationAction extends Action {

    private Mech attackerMech;
    private Mech defenderMech;
    private double counter = 0;
    private int duration = 0;
    private int minRange;
    private List<Weapon> selected;

    public AttackAnimationAction(Mech attackerMech, Mech defenderMech, int minRange) {
        this.minRange = minRange;
        this.attackerMech = attackerMech;
        this.defenderMech = defenderMech;
        selected = new ArrayList<>(attackerMech.getSelectedWeapons());
    }

    @Override
    public boolean act(float delta) {

        if(attackerMech.getX() < defenderMech.getX())
            attackerMech.setDirection(Direction.Right);
        else {
            attackerMech.setDirection(Direction.Left);
        }

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

        if (duration >= attackerMech.getSelectedWeapons().size() && duration > 1) {
            System.out.println("Ennyiszer: " + attackerMech.getSelectedWeapons().size());
            attackerMech.setState(State.Idle);
            return true;
        }
        return false;
    }
}
