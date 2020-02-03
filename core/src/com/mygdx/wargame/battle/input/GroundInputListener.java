package com.mygdx.wargame.battle.input;

import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.mygdx.wargame.battle.controller.SelectionController;
import com.mygdx.wargame.battle.lock.ActionLock;
import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.battle.map.Node;
import com.mygdx.wargame.mech.AbstractMech;
import com.mygdx.wargame.battle.unit.action.MovementAction;

public class GroundInputListener extends InputListener {

    private SelectionController selectionController;
    private BattleMap battleMap;
    private Node node;
    private ActionLock actionLock;

    public GroundInputListener(SelectionController selectionController, BattleMap battleMap, Node node, ActionLock actionLock) {
        this.selectionController = selectionController;
        this.battleMap = battleMap;
        this.node = node;
        this.actionLock = actionLock;
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

        if (actionLock.isLocked())
            return true;

        AbstractMech attacker = selectionController.getSelected();

        if (attacker != null) {

            Node start = battleMap.getNodeGraphLv1().getNodeWeb()[(int) attacker.getX()][(int) attacker.getY()];
            Node end = battleMap.getNodeGraphLv1().getNodeWeb()[(int) node.getX()][(int) node.getY()];

            // reconnect so that attacker can move
            battleMap.getNodeGraphLv1().reconnectCities(battleMap.getNodeGraphLv1().getNodeWeb()[(int)attacker.getX()][(int)attacker.getY()]);

            GraphPath<Node> paths = battleMap.calculatePath(start, end);
            battleMap.addPath(attacker, paths);
            attacker.addAction(new MovementAction(battleMap, attacker, actionLock));
        }

        return true;
    }
}