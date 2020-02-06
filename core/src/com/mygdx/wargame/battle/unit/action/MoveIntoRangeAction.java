package com.mygdx.wargame.battle.unit.action;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.battle.map.Node;
import com.mygdx.wargame.battle.unit.Direction;
import com.mygdx.wargame.battle.unit.State;
import com.mygdx.wargame.mech.Mech;
import com.mygdx.wargame.pilot.Pilot;
import com.mygdx.wargame.rules.calculator.RangeCalculator;
import com.mygdx.wargame.util.MathUtils;

import java.util.List;

import static com.mygdx.wargame.battle.unit.Direction.Right;

public class MoveIntoRangeAction extends Action {

    private BattleMap battleMap;
    private Mech attackerMech;
    private Pilot attackerPilot;
    private Mech defender;
    private float counter = 0.0f;
    private RangeCalculator rangeCalculator;
    private int minRange;

    public MoveIntoRangeAction(BattleMap battleMap, Mech attackerMech, Pilot attackerPilot, Mech defender, RangeCalculator rangeCalculator) {
        this.battleMap = battleMap;
        this.attackerMech = attackerMech;
        this.attackerPilot = attackerPilot;
        this.defender = defender;
        this.rangeCalculator = rangeCalculator;
        battleMap.getNodeGraphLv1().reconnectCities(battleMap.getNodeGraphLv1().getNodeWeb()[(int) attackerMech.getX()][(int) attackerMech.getY()]);
        minRange = rangeCalculator.calculateAllWeaponsRange(attackerPilot, attackerMech);
    }

    @Override
    public boolean act(float delta) {
        counter += delta;

        if (counter > 0.15f) {
            counter = 0.0f;
            attackerMech.setState(State.Walk);
            List<Node> nodes = battleMap.getPath(attackerMech);


            // no more nodes left to move
            if (nodes.isEmpty() || MathUtils.getDistance(attackerMech.getX(), attackerMech.getY(), defender.getX(), defender.getY()) <= minRange) {
                attackerMech.setState(State.Idle);
                battleMap.setTemporaryObstacle((int) attackerMech.getX(), (int) attackerMech.getY());
                attackerMech.setMoved(true);
                return true;
            }

            // no more movement points left to move
            if (attackerMech.getMovementPoints() <= 0) {

                battleMap.setTemporaryObstacle((int) attackerMech.getX(), (int) attackerMech.getY());
                attackerMech.resetMovementPoints(0);
                attackerMech.setState(State.Idle);
                attackerMech.setMoved(true);
                return true;
            }

            Node nextNode = nodes.remove(0);
            if (nextNode.getX() != attackerMech.getX()) {
                attackerMech.setDirection(nextNode.getX() < attackerMech.getX() ? Direction.Left : Right);
            }
            attackerMech.consumeMovementPoint(1);
            attackerMech.setPosition(nextNode.getX(), nextNode.getY());
            return false;
        }
        return false;
    }
}
