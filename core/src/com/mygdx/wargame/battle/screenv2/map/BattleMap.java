package com.mygdx.wargame.battle.screenv2.map;

import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.mygdx.wargame.battle.map.Node;
import com.mygdx.wargame.battle.map.NodeGraph;
import com.mygdx.wargame.battle.screenv2.AssetManagerLoaderV2;
import com.mygdx.wargame.battle.screenv2.TiledMapGenerator;
import com.mygdx.wargame.battle.screenv2.tile.TileSets;

public class BattleMap {

    public static final int WIDTH = 15;
    public static final int HEIGHT = 15;
    private TiledMap tiledMap;
    private NodeGraph nodeGraph;

    public BattleMap(AssetManagerLoaderV2 assetManagerLoaderV2) {
        this.tiledMap = new TiledMapGenerator(assetManagerLoaderV2).generate(WIDTH, HEIGHT, TileSets.GrassLand);
        this.nodeGraph = new NodeGraph(WIDTH, HEIGHT);

        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                Node node;

                if (nodeGraph.getNodeWeb()[i][j] == null) {
                    node = new Node(i, j);
                    nodeGraph.addNode(node);
                } else {
                    node = nodeGraph.getNodeWeb()[i][j];
                }

                addNodeIfDoesntExists(node, i - 1, j, nodeGraph);
                //addNodeIfDoesntExists(node, i - 1, j - 1, nodeGraph);
                //addNodeIfDoesntExists(node, i - 1, j + 1, nodeGraph);
                addNodeIfDoesntExists(node, i, j - 1, nodeGraph);
                addNodeIfDoesntExists(node, i, j + 1, nodeGraph);
                addNodeIfDoesntExists(node, i + 1, j, nodeGraph);
                //addNodeIfDoesntExists(node, i + 1, j - 1, nodeGraph);
                //addNodeIfDoesntExists(node, i + 1, j + 1, nodeGraph);
            }
        }
        System.out.println("done");
    }


    public GraphPath<Node> calculatePath(Node s, Node g) {
        System.out.println("From: " + s.getX()+ " " + s.getY());
        System.out.println("To: " + g.getX()+ " " + g.getY());
        return nodeGraph.findPath(s, g);
    }

    private void addNodeIfDoesntExists(Node node, int i, int j, NodeGraph nodeGraph) {
        if (i < 0 || i >= WIDTH || j < 0 || j >= HEIGHT) {
            return;
        }

        Node newNode;
        if (nodeGraph.getNodeWeb()[i][j] == null) {
            newNode = new Node(i, j);
            nodeGraph.addNode(newNode);
        } else {
            newNode = nodeGraph.getNodeWeb()[i][j];
        }

        nodeGraph.connectCities(node, newNode);
    }

    public TiledMap getTiledMap() {
        return tiledMap;
    }

    public NodeGraph getNodeGraph() {
        return nodeGraph;
    }
}
