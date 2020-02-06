package com.mygdx.wargame.battle.unit.action;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.mech.Mech;
import com.mygdx.wargame.pilot.Pilot;
import com.mygdx.wargame.rules.facade.AttackFacade;

public class AttackAction extends Action {

    private AttackFacade attackFacade;
    private Mech attackerMech;
    private Pilot attackingPilot;
    private Mech defenderMech;
    private Pilot defenderPilot;
    private BattleMap battleMap;

    public AttackAction(AttackFacade attackFacade, Mech attackerMech, Pilot attackingPilot, Mech defenderMech, Pilot defenderPilot, BattleMap battleMap) {
        this.attackFacade = attackFacade;
        this.attackerMech = attackerMech;
        this.attackingPilot = attackingPilot;
        this.defenderMech = defenderMech;
        this.defenderPilot = defenderPilot;
        this.battleMap = battleMap;
    }


    @Override
    public boolean act(float delta) {
        attackFacade.attack(attackingPilot, attackerMech, defenderPilot, defenderMech, battleMap, null);
        return true;
    }
}
