package com.mygdx.wargame.battle.unit.action;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.battle.map.Node;
import com.mygdx.wargame.battle.unit.AbstractMech;

import java.util.List;

public class MovementAction extends Action {

    private BattleMap battleMap;
    private AbstractMech abstractMech;

    public MovementAction(BattleMap battleMap, AbstractMech abstractMech) {
        this.battleMap = battleMap;
        this.abstractMech = abstractMech;
    }

    @Override
    public boolean act(float delta) {
        List<Node> nodes = battleMap.getPath(abstractMech);
        if (nodes.isEmpty() || abstractMech.getMovementPoints() <= 0) {
            abstractMech.setMovementPoints(0);
            return true;
        }

        Node nextNode = nodes.remove(0);
        abstractMech.consumeMovementPoint(1);
        abstractMech.setPosition(nextNode.getX(), nextNode.getY());
        return false;
    }
}
