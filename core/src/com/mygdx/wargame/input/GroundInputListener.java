package com.mygdx.wargame.input;

import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.mygdx.wargame.battle.controller.SelectionController;
import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.battle.map.Node;
import com.mygdx.wargame.battle.map.NodeGraph;
import com.mygdx.wargame.battle.unit.AbstractMech;
import com.mygdx.wargame.battle.unit.action.MovementAction;

public class GroundInputListener extends InputListener {

    private SelectionController selectionController;
    private NodeGraph nodeGraph;
    private BattleMap battleMap;
    private Node node;

    public GroundInputListener(SelectionController selectionController, NodeGraph nodeGraph, BattleMap battleMap, Node node) {
        this.selectionController = selectionController;
        this.nodeGraph = nodeGraph;
        this.battleMap = battleMap;
        this.node = node;
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

        AbstractMech attacker = selectionController.getSelected();

        if (attacker != null) {

            Node start = nodeGraph.getNodeWeb()[(int) attacker.getX()][(int) attacker.getY()];
            Node end = nodeGraph.getNodeWeb()[(int) node.getX()][(int) node.getY()];

            // reconnect so that attacker can move
            nodeGraph.reconnectCities(battleMap.getNodeGraphLv1().getNodeWeb()[(int)attacker.getX()][(int)attacker.getY()]);

            GraphPath<Node> paths = battleMap.calculatePath(start, end, 50);
            battleMap.addPath(attacker, paths);
            attacker.addAction(new MovementAction(battleMap, attacker));
        }

        return true;
    }
}