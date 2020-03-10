package com.mygdx.wargame.battle.map;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

public class NodeGraph implements IndexedGraph<Node> {

    private int width, height;
    private NodeHeuristic nodeHeuristic = new NodeHeuristic();
    private Array<Node> nodes = new Array<>();
    private Array<Edge> edges = new Array<>();
    private Node[][] nodeWeb;
    private boolean[][] impassable;
    private ObjectMap<Node, Array<Edge>> nodeEdges = new ObjectMap<>();
    ObjectMap<Node, Array<Connection<Node>>> streetsMap = new ObjectMap<>();
    private int lastNodeIndex = 0;
    IndexedAStarPathFinder<Node> indexedAStarPathFinder;

    public NodeGraph(int width, int height) {
        this.width = width;
        this.height = height;
        nodeWeb = new Node[width][height];
        impassable = new boolean[width][height];
    }

    public void addNode(Node node) {
        node.setIndex(lastNodeIndex);
        nodeWeb[(int) node.getX()][(int) node.getY()] = node;
        lastNodeIndex++;

        nodes.add(node);
    }

    public void reconnectCities(Node fromNode) {
        int x = (int) fromNode.getX();
        int y = (int) fromNode.getY();

        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {

                if (i == 1 && j == 1)
                    continue;

                if (x + i < 0 || y + j < 0 || x + i >= width || y + j >= height)
                    continue;

                connectCities(fromNode, nodeWeb[x + i][y + j]);
            }
        }
    }

    public void connectCities(Node fromNode, Node toNode) {
        if (impassable[(int) fromNode.getX()][(int) fromNode.getY()] && impassable[(int) toNode.getX()][(int) toNode.getY()])
            return;

        Edge edge = new Edge(fromNode, toNode);
        if (!streetsMap.containsKey(fromNode)) {
            streetsMap.put(fromNode, new Array<Connection<Node>>());
        }
        if (!streetsMap.get(fromNode).contains(edge, true)) {
            streetsMap.get(fromNode).add(edge);
            edges.add(edge);

            if (!nodeEdges.containsKey(fromNode)) {
                nodeEdges.put(fromNode, new Array<>());
            }
            nodeEdges.get(fromNode).add(edge);
        }
    }

    public void disconnectCities(Node fromNode) {
        streetsMap.get(fromNode).clear();
        edges.removeAll(nodeEdges.get(fromNode), true);
        nodeEdges.get(fromNode).clear();
    }

    public GraphPath<Node> findPath(Node startNode, Node toNode) {
        GraphPath<Node> cityPath = new DefaultGraphPath<>();
        if (indexedAStarPathFinder == null) {
            indexedAStarPathFinder = new IndexedAStarPathFinder<>(this);
        }

        indexedAStarPathFinder.searchNodePath(startNode, toNode, nodeHeuristic, cityPath);
        return cityPath;
    }

    @Override
    public int getIndex(Node node) {
        return node.getIndex();
    }

    @Override
    public int getNodeCount() {
        return lastNodeIndex;
    }

    @Override
    public Array<Connection<Node>> getConnections(Node fromNode) {
        if (streetsMap.containsKey(fromNode)) {
            return streetsMap.get(fromNode);
        }

        return new Array<>(0);
    }

    public Node[][] getNodeWeb() {
        return nodeWeb;
    }

    public Array<Node> getNodes() {
        return nodes;
    }

    public Array<Edge> getEdges() {
        return edges;
    }

    public ObjectMap<Node, Array<Edge>> getNodeEdges() {
        return nodeEdges;
    }

    public void setImpassable(float x, float y) {
        impassable[(int) x][(int) y] = true;
        disconnectCities(nodeWeb[(int) x][(int) y]);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
