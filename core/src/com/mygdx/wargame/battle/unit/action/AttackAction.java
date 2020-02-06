package com.mygdx.wargame.battle.unit.action;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.mech.Mech;
import com.mygdx.wargame.pilot.Pilot;
import com.mygdx.wargame.rules.calculator.RangeCalculator;
import com.mygdx.wargame.rules.facade.AttackFacade;
import com.mygdx.wargame.util.MathUtils;

import java.util.Comparator;

public class AttackAction extends Action {

    private AttackFacade attackFacade;
    private Mech attackerMech;
    private Pilot attackingPilot;
    private Mech defenderMech;
    private Pilot defenderPilot;
    private BattleMap battleMap;
    private RangeCalculator rangeCalculator;

    public AttackAction(AttackFacade attackFacade, Mech attackerMech, Pilot attackingPilot, Mech defenderMech, Pilot defenderPilot, BattleMap battleMap, RangeCalculator rangeCalculator) {
        this.attackFacade = attackFacade;
        this.attackerMech = attackerMech;
        this.attackingPilot = attackingPilot;
        this.defenderMech = defenderMech;
        this.defenderPilot = defenderPilot;
        this.battleMap = battleMap;
        this.rangeCalculator = rangeCalculator;
    }


    @Override
    public boolean act(float delta) {

        int minRange = attackerMech.getSelectedWeapons()
                .stream()
                .map(w -> rangeCalculator.calculate(attackingPilot, w))
                .min(Comparator.naturalOrder()).orElse(0);

        attackerMech.setAttacked(true);
        attackerMech.setMoved(true);

        if(MathUtils.getDistance(attackerMech.getX(), attackerMech.getY(), defenderMech.getX(), defenderMech.getY()) > minRange) {
            // not in range, can't do anything
            return true;
        }

        attackFacade.attack(attackingPilot, attackerMech, defenderPilot, defenderMech, battleMap, null);
        return true;
    }
}
