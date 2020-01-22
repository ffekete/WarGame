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
    ObjectMap<Node, Array<Connection<Node>>> streetsMap = new ObjectMap<>();
    private int lastNodeIndex = 0;

    public NodeGraph(int width, int height) {
        this.width = width;
        this.height = height;
         nodeWeb = new Node[width][height];
    }

    public void addNode(Node node){
        node.setIndex(lastNodeIndex);
        nodeWeb[(int)node.getX()][(int)node.getY()] = node;
        lastNodeIndex++;

        nodes.add(node);
    }

    public void connectCities(Node fromNode, Node toNode){
        Edge edge = new Edge(fromNode, toNode);
        if(!streetsMap.containsKey(fromNode)){
            streetsMap.put(fromNode, new Array<Connection<Node>>());
        }
        streetsMap.get(fromNode).add(edge);
        edges.add(edge);
    }

    public GraphPath<Node> findPath(Node startNode, Node toNode){
        GraphPath<Node> cityPath = new DefaultGraphPath<>();
        new IndexedAStarPathFinder<>(this).searchNodePath(startNode, toNode, nodeHeuristic, cityPath);
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
        if(streetsMap.containsKey(fromNode)){
            return streetsMap.get(fromNode);
        }

        return new Array<>(0);
    }

    public Node[][] getNodeWeb() {
        return nodeWeb;
    }
}
