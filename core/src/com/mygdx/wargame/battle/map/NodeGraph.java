package com.mygdx.wargame.battle.map;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

        reconnect(x, y, fromNode);
    }

    public void reconnectCities(int x, int y) {
        Node fromNode = nodeWeb[x][y];
        reconnect(x, y, fromNode);
    }

    private void reconnect(int x, int y, Node fromNode) {
        if (y + 1 < height) {
            connectCities(nodeWeb[x][y + 1], fromNode);
            connectCities(fromNode, nodeWeb[x][y + 1]);
        }

        if (y - 1 >= 0) {
            connectCities(nodeWeb[x][y - 1], fromNode);
            connectCities(fromNode, nodeWeb[x][y - 1]);
        }

        if (x - 1 >= 0) {
            connectCities(nodeWeb[x - 1][y], fromNode);
            connectCities(fromNode, nodeWeb[x - 1][y]);
        }

        if (x + 1 < width) {
            connectCities(nodeWeb[x + 1][y], fromNode);
            connectCities(fromNode, nodeWeb[x + 1][y]);
        }
    }

    public void connectCities(Node fromNode, Node toNode) {
        if (impassable[(int) fromNode.getX()][(int) fromNode.getY()] || impassable[(int) toNode.getX()][(int) toNode.getY()])
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
        int x = (int)fromNode.getX();
        int y = (int)fromNode.getY();
        disconnect(fromNode, x, y - 1);
        disconnect(fromNode, x, y + 1);
        disconnect(fromNode, x - 1, y);
        disconnect(fromNode, x + 1, y);
        streetsMap.get(fromNode).clear();

    }

    public void disconnectCities(int x, int y) {
        Node fromNode = nodeWeb[x][y];

        disconnect(fromNode, x, y - 1);
        disconnect(fromNode, x, y + 1);
        disconnect(fromNode, x - 1, y);
        disconnect(fromNode, x + 1, y);
        streetsMap.get(fromNode).clear();
    }

    private void disconnect(Node fromNode, int x, int y) {
        if (x < 0 || y < 0 || x >= BattleMap.WIDTH || y >= BattleMap.HEIGHT) {
            return;
        }
        Node neighbour = nodeWeb[x][y];
        List<Connection<Node>> nodeConnections = Arrays.stream(streetsMap.get(neighbour).items)
                .filter(edge -> edge != null && edge.getToNode() == fromNode)
                .collect(Collectors.toList());

        nodeConnections.forEach(nodeConnection -> {
            streetsMap.get(neighbour).removeValue(nodeConnection, true);
        });

        Arrays.stream(edges.items).filter(
                edge -> edge != null && edge.getToNode() == fromNode
        ).forEach(edge -> {
            edges.removeValue(edge, true);
        });


        edges.removeAll(nodeEdges.get(fromNode), true);
        nodeEdges.get(fromNode).clear();

        nodeEdges.forEach(edges -> {
            Arrays.stream(edges.value.items).filter(edge -> edge != null && edge.getToNode() == fromNode)
                    .forEach(entry -> nodeEdges.get(edges.key).removeValue(entry, true));
        });
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
