package com.mygdx.wargame.battle.unit.action;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.mygdx.wargame.battle.lock.ActionLock;
import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.battle.map.Node;
import com.mygdx.wargame.mech.AbstractMech;
import com.mygdx.wargame.battle.unit.Direction;
import com.mygdx.wargame.battle.unit.State;
import com.mygdx.wargame.mech.Mech;

import java.util.List;

import static com.mygdx.wargame.battle.unit.Direction.Right;

public class MovementAction extends Action {

    private BattleMap battleMap;
    private Mech abstractMech;
    private float counter = 0.0f;
    private ActionLock actionLock;

    public MovementAction(BattleMap battleMap, Mech abstractMech, ActionLock actionLock) {
        this.battleMap = battleMap;
        this.abstractMech = abstractMech;
        this.actionLock = actionLock;
    }

    @Override
    public boolean act(float delta) {
        counter += delta;
        actionLock.setLocked(true);

        if (counter > 0.15f) {
            counter = 0.0f;
            abstractMech.setState(State.Walk);
            List<Node> nodes = battleMap.getPath(abstractMech);

            // no more nodes left to move
            if (nodes.isEmpty()) {
                abstractMech.setState(State.Idle);
                battleMap.setTemporaryObstacle((int) abstractMech.getX(), (int) abstractMech.getY());
                actionLock.setLocked(false);
                abstractMech.setMoved(true);
                abstractMech.setAttacked(true);// todo remove later
                return true;
            }

            // no more movement points left to move
            if (abstractMech.getMovementPoints() <= 0) {
                abstractMech.resetMovementPoints(0);
                abstractMech.setState(State.Idle);
                actionLock.setLocked(false);
                abstractMech.setMoved(true);
                abstractMech.setAttacked(true);// todo remove later
                battleMap.setTemporaryObstacle((int) abstractMech.getX(), (int) abstractMech.getY());
                return true;
            }

            Node nextNode = nodes.remove(0);
            if (nextNode.getX() != abstractMech.getX()) {
                abstractMech.setDirection(nextNode.getX() < abstractMech.getX() ? Direction.Left : Right);
            }
            abstractMech.consumeMovementPoint(1);
            abstractMech.setPosition(nextNode.getX(), nextNode.getY());
            return false;
        }
        return false;
    }
}
