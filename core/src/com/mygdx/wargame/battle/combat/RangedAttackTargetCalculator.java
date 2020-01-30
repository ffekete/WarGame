package com.mygdx.wargame.battle.combat;

import com.badlogic.gdx.ai.pfa.GraphPath;
import com.mygdx.wargame.battle.lock.ActionLock;
import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.battle.map.Node;
import com.mygdx.wargame.battle.unit.AbstractMech;
import com.mygdx.wargame.battle.unit.action.MoveAndAttackAction;

public class RangedAttackTargetCalculator implements AttackCalculator {

    private BattleMap battleMap;
    private ActionLock actionLock;

    public RangedAttackTargetCalculator(BattleMap battleMap, ActionLock actionLock) {
        this.battleMap = battleMap;
        this.actionLock = actionLock;
    }

    @Override
    public void calculate(AbstractMech attacker, AbstractMech defender) {

        if (attacker != null && defender != null) {
            Node start = battleMap.getNodeGraphLv1().getNodeWeb()[(int) attacker.getX()][(int) attacker.getY()];
            Node end = battleMap.getNodeGraphLv1().getNodeWeb()[(int) defender.getX()][(int) defender.getY()];

            // reconnect so that attacker can move
            battleMap.getNodeGraphLv1().reconnectCities(battleMap.getNodeGraphLv1().getNodeWeb()[(int)attacker.getX()][(int)attacker.getY()]);

            GraphPath<Node> paths = battleMap.calculatePath(start, end);
            battleMap.addPath(attacker, paths);
            attacker.addAction(new MoveAndAttackAction(battleMap, attacker, defender, actionLock));
        }
    }
}
