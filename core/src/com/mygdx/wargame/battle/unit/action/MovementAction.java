package com.mygdx.wargame.battle.unit.action;

import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.battle.map.Node;
import com.mygdx.wargame.battle.unit.AbstractWarrior;

import java.util.List;
import java.util.Set;

public class MovementAction extends Action {

    private BattleMap battleMap;
    private AbstractWarrior abstractWarrior;
    private Stage stage;
    private boolean notStartedYet = true;

    public MovementAction(BattleMap battleMap, AbstractWarrior abstractWarrior, Stage stage) {
        this.battleMap = battleMap;
        this.abstractWarrior = abstractWarrior;
        this.stage = stage;
    }

    @Override
    public boolean act(float delta) {
        List<Node> nodes = battleMap.getPath(abstractWarrior);
        if (nodes.isEmpty()) {
            return true;
        }

        Node nextNode = nodes.remove(0);
        abstractWarrior.setPosition(nextNode.getX(), nextNode.getY());
        return false;
    }
}
