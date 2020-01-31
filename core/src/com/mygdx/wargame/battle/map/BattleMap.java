package com.mygdx.wargame.battle.map;

import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.mygdx.wargame.battle.controller.SelectionController;
import com.mygdx.wargame.battle.lock.ActionLock;
import com.mygdx.wargame.mech.AbstractMech;
import com.mygdx.wargame.battle.input.GroundInputListener;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class BattleMap {

    int width, height;
    private NodeGraph nodeGraphLv1;
    private SelectionController selectionController;
    private Stage stage;
    private ActionLock actionLock;

    private Map<AbstractMech, List<Node>> paths = new HashMap<>();

    public BattleMap(int x, int y, SelectionController selectionController, Stage stage, ActionLock actionLock) {
        this.width = x;
        this.height = y;
        this.selectionController = selectionController;
        this.stage = stage;
        this.actionLock = actionLock;

        this.nodeGraphLv1 = new NodeGraph(width, height);

        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                Node node;

                if (nodeGraphLv1.getNodeWeb()[i][j] == null) {
                    node = new Node(i, j);
                    GroundInputListener groundInputListener = new GroundInputListener(this.selectionController, this, node, actionLock);
                    node.addListener(groundInputListener);
                    node.setTouchable(Touchable.enabled);
                    stage.addActor(node);
                    node.setSize(1, 1);
                } else {
                    node = nodeGraphLv1.getNodeWeb()[i][j];
                }

                nodeGraphLv1.addNode(node);

                addNodeIfDoesntExists(node, i - 1, j, nodeGraphLv1);
                addNodeIfDoesntExists(node, i - 1, j - 1, nodeGraphLv1);
                addNodeIfDoesntExists(node, i - 1, j + 1, nodeGraphLv1);
                addNodeIfDoesntExists(node, i, j - 1, nodeGraphLv1);
                addNodeIfDoesntExists(node, i, j + 1, nodeGraphLv1);
                addNodeIfDoesntExists(node, i + 1, j, nodeGraphLv1);
                addNodeIfDoesntExists(node, i + 1, j - 1, nodeGraphLv1);
                addNodeIfDoesntExists(node, i + 1, j + 1, nodeGraphLv1);
            }
        }
    }

    private void addNodeIfDoesntExists(Node node, int i, int j, NodeGraph nodeGraph) {
        if (i < 0 || i >= width || j < 0 || j >= height) {
            return;
        }

        Node newNode;
        if (nodeGraph.getNodeWeb()[i][j] == null) {
            newNode = new Node(i, j);
            GroundInputListener groundInputListener = new GroundInputListener(this.selectionController, this, node, actionLock);
            node.addListener(groundInputListener);
            node.setTouchable(Touchable.enabled);
            stage.addActor(node);
            node.setSize(1, 1);
        } else {
            newNode = nodeGraph.getNodeWeb()[i][j];
        }

        nodeGraph.addNode(newNode);
        nodeGraph.connectCities(node, newNode);
        nodeGraph.connectCities(newNode, node);
    }

    public GraphPath<Node> calculatePath(Node s, Node g) {
        return nodeGraphLv1.findPath(s, g);
    }

    public void addPath(AbstractMech man, GraphPath<Node> path) {
        Iterator<Node> it = path.iterator();
        paths.computeIfAbsent(man, v -> new LinkedList<>());
        while (it.hasNext()) {
            paths.get(man).add(it.next());
        }
    }

    public void setPermanentObstacle(float x, float y) {
        nodeGraphLv1.setImpassable(x, y);
    }

    public void setTemporaryObstacle(float x, float y) {
        nodeGraphLv1.disconnectCities(nodeGraphLv1.getNodeWeb()[(int) x][(int) y]);
    }

    public List<Node> getPath(AbstractMech abstractMech) {
        return paths.get(abstractMech);
    }

    public NodeGraph getNodeGraphLv1() {
        return nodeGraphLv1;
    }
}
