package com.mygdx.wargame.battle.map;

import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.mygdx.wargame.battle.unit.AbstractWarrior;
import com.mygdx.wargame.battle.unit.Man;
import com.sun.org.apache.xalan.internal.xsltc.dom.NodeSortRecord;

import java.util.*;

public class BattleMap {

    int width, height;

    NodeGraph nodeGraph;
    private Map<AbstractWarrior, List<Node>> paths = new HashMap<>();

    public BattleMap(int x, int y) {
        this.width = x;
        this.height = y;

        this.nodeGraph = new NodeGraph(width, height);

        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                Node node;

                if (nodeGraph.getNodeWeb()[i][j] == null) {
                    node = new Node(i, j);
                } else {
                    node = nodeGraph.getNodeWeb()[i][j];
                }

                nodeGraph.addNode(node);

                addNodeIfDoesntExisit(node, i - 1, j, nodeGraph);
                addNodeIfDoesntExisit(node, i - 1, j - 1, nodeGraph);
                addNodeIfDoesntExisit(node, i - 1, j + 1, nodeGraph);
                addNodeIfDoesntExisit(node, i, j - 1, nodeGraph);
                addNodeIfDoesntExisit(node, i, j + 1, nodeGraph);
                addNodeIfDoesntExisit(node, i + 1, j, nodeGraph);
                addNodeIfDoesntExisit(node, i + 1, j - 1, nodeGraph);
                addNodeIfDoesntExisit(node, i + 1, j + 1, nodeGraph);
            }
        }

    }

    private void addNodeIfDoesntExisit(Node node, int i, int j, NodeGraph nodeGraph) {
        if (i < 0 || i >= width || j < 0 || j >= height) {
            return;
        }

        Node newNode;
        if (nodeGraph.getNodeWeb()[i][j] == null) {
            newNode = new Node(i,j);
        } else {
            newNode = nodeGraph.getNodeWeb()[i][j];
        }

        nodeGraph.addNode(newNode);
        nodeGraph.connectCities(node, newNode);
        nodeGraph.connectCities(newNode, node);
    }

    public GraphPath<Node> calculatePath(Node s, Node g, int proximity) {
        return nodeGraph.findPath(s, g);
    }

    public void addPath(AbstractWarrior man, GraphPath<Node> path) {
        Iterator<Node> it = path.iterator();
        paths.computeIfAbsent(man, v -> new LinkedList<>());
        while(it.hasNext()) {
            paths.get(man).add(it.next());
        }

    }

    public void setObstacle(float x, float y, int value) {

    }

    public List<Node> getPath(AbstractWarrior abstractWarrior) {
        return paths.get(abstractWarrior);
    }

    public NodeGraph getNodeGraph() {
        return nodeGraph;
    }
}
