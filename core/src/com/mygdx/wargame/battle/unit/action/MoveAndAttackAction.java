package com.mygdx.wargame.battle.unit.action;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.battle.map.Node;
import com.mygdx.wargame.battle.unit.AbstractMech;
import com.mygdx.wargame.battle.unit.Direction;
import com.mygdx.wargame.battle.unit.State;
import com.mygdx.wargame.util.MathUtils;

import java.util.List;

import static com.mygdx.wargame.battle.unit.Direction.Right;

public class MoveAndAttackAction extends Action {

    private BattleMap battleMap;
    private AbstractMech attacker;
    private AbstractMech defender;
    private float counter = 0.0f;

    public MoveAndAttackAction(BattleMap battleMap, AbstractMech attacker, AbstractMech defender) {
        this.battleMap = battleMap;
        this.attacker = attacker;
        this.defender = defender;
        battleMap.getNodeGraphLv1().reconnectCities(battleMap.getNodeGraphLv1().getNodeWeb()[(int) attacker.getX()][(int) attacker.getY()]);
    }

    @Override
    public boolean act(float delta) {
        counter += delta;

        if (counter > 0.15f) {
            counter = 0.0f;
            attacker.setState(State.Walk);
            List<Node> nodes = battleMap.getPath(attacker);

            // no more nodes left to mov
            if (nodes.isEmpty() || MathUtils.getDistance(attacker.getX(), attacker.getY(), defender.getX(), defender.getY()) <= attacker.getRange()) {
                attacker.setState(State.Idle);
                battleMap.setTemporaryObstacle((int) attacker.getX(), (int) attacker.getY());
                // todo add attack here
                return true;
            }

            // no more movement points left to move
            if (attacker.getMovementPoints() <= 0) {

                battleMap.setTemporaryObstacle((int) attacker.getX(), (int) attacker.getY());
                attacker.setMovementPoints(0);
                attacker.setState(State.Idle);
                return true;
            }

            Node nextNode = nodes.remove(0);
            if (nextNode.getX() != attacker.getX()) {
                attacker.setDirection(nextNode.getX() < attacker.getX() ? Direction.Left : Right);
            }
            attacker.consumeMovementPoint(1);
            attacker.setPosition(nextNode.getX(), nextNode.getY());
            return false;
        }
        return false;
    }
}
