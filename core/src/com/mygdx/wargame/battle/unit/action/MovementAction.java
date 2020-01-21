package com.mygdx.wargame.battle.unit.action;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.battle.map.Node;
import com.mygdx.wargame.battle.unit.AbstractWarrior;

import java.util.List;

public class MovementAction extends Action {

    private BattleMap battleMap;
    private AbstractWarrior abstractWarrior;
    private Stage stage;

    public MovementAction(BattleMap battleMap, AbstractWarrior abstractWarrior, Stage stage) {
        this.battleMap = battleMap;
        this.abstractWarrior = abstractWarrior;
        this.stage = stage;
    }

    @Override
    public boolean act(float delta) {
        List<Node> nodes = battleMap.getPath(abstractWarrior);
        if(nodes.isEmpty()) {
            return true;
        }
        abstractWarrior.setPosition(nodes.get(nodes.size() -1).getX(), nodes.get(nodes.size() -1).getY());

        //stage.getActors().removeValue(nodes.get(nodes.size() -1), true);
        battleMap.getPath(abstractWarrior).remove(nodes.get(nodes.size() -1));
        return false;
    }
}
