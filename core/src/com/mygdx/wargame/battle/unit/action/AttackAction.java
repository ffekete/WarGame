package com.mygdx.wargame.battle.unit.action;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.battle.rules.facade.AttackFacade;
import com.mygdx.wargame.common.mech.BodyPart;
import com.mygdx.wargame.common.mech.Mech;
import com.mygdx.wargame.common.pilot.Pilot;
import com.mygdx.wargame.util.MathUtils;

public class AttackAction extends Action {

    private AttackFacade attackFacade;
    private Mech attackerMech;
    private Pilot attackingPilot;
    private Mech defenderMech;
    private Pilot defenderPilot;
    private BattleMap battleMap;
    private int minRange;
    private BodyPart bodyPart;

    public AttackAction(AttackFacade attackFacade, Mech attackerMech, Pilot attackingPilot, Mech defenderMech, Pilot defenderPilot, BattleMap battleMap, int minRange, BodyPart bodyPart) {
        this.attackFacade = attackFacade;
        this.attackerMech = attackerMech;
        this.attackingPilot = attackingPilot;
        this.defenderMech = defenderMech;
        this.defenderPilot = defenderPilot;
        this.battleMap = battleMap;
        this.minRange = minRange;
        this.bodyPart = bodyPart;
    }


    @Override
    public boolean act(float delta) {

        attackerMech.setAttacked(true);
        attackerMech.setMoved(true);

        if (MathUtils.getDistance(attackerMech.getX(), attackerMech.getY(), defenderMech.getX(), defenderMech.getY()) > minRange) {
            // not in range, can't do anything
            return true;
        }

        attackFacade.attack(attackingPilot, attackerMech, defenderPilot, defenderMech, battleMap, bodyPart);
        return true;
    }
}
