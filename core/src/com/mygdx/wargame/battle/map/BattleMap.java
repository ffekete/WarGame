package com.mygdx.wargame.battle.map;

import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.mygdx.wargame.battle.map.decoration.DrawableTile;
import com.mygdx.wargame.battle.map.decoration.DrawableTiledMapTile;
import com.mygdx.wargame.battle.map.info.MovementPathMarker;
import com.mygdx.wargame.battle.screen.AssetManagerLoaderV2;

public class BattleMap {

    public static final int WIDTH = 10;
    public static final int HEIGHT = 10;
    private TiledMap tiledMap;
    private com.mygdx.wargame.battle.map.NodeGraph nodeGraph;
    private TerrainType terrainType;
    private boolean[][] markers = new boolean[WIDTH][HEIGHT];
    private AssetManagerLoaderV2 assetManagerLoaderV2;

    public BattleMap(AssetManagerLoaderV2 assetManagerLoaderV2, TerrainType terrainType, AssetManagerLoaderV2 assetManagerLoaderV21) {

        this.terrainType = terrainType;
        this.assetManagerLoaderV2 = assetManagerLoaderV21;

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
                addNodeIfDoesntExists(node, i, j - 1, nodeGraph);
                addNodeIfDoesntExists(node, i, j + 1, nodeGraph);
                addNodeIfDoesntExists(node, i + 1, j, nodeGraph);

            }
        }

        this.tiledMap = new TiledMapGenerator(assetManagerLoaderV2).generate(nodeGraph, WIDTH, HEIGHT, terrainType.getTileSets());

        System.out.println("done");
    }


    public GraphPath<Node> calculatePath(Node s, Node g) {
        System.out.println("From: " + s.getX() + " " + s.getY());
        System.out.println("To: " + g.getX() + " " + g.getY());
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

    public void setTemporaryObstacle(float x, float y) {
        nodeGraph.disconnectCities(nodeGraph.getNodeWeb()[(int) x][(int) y]);
    }

    public TiledMap getTiledMap() {
        return tiledMap;
    }

    public NodeGraph getNodeGraph() {
        return nodeGraph;
    }

    public TerrainType getTerrainType() {
        return terrainType;
    }

    public boolean getMarker(int x, int y) {
        if(x < 0 || y < 0 || x >= WIDTH || y >= HEIGHT) {
            return false;
        }
        return markers[x][y];
    }

    public void toggleMarker(int x, int y, boolean  value) {
        if(x < 0 || y < 0 || x >= WIDTH || y >= HEIGHT) {
            return;
        }
        this.markers[x][y] = value;
    }

    public void addMarker(int x, int y) {

        TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();

        cell.setTile(new DrawableTiledMapTile(new DrawableTile(assetManagerLoaderV2.getAssetManager().get("info/PathMarker.png", Texture.class), this, x, y)));
        TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get("pathLayer");
        layer.setCell(x, y, cell);
    }

    public void clearMarkers() {
         tiledMap.getLayers().get("pathLayer").getObjects().forEach(o -> tiledMap.getLayers().get("pathLayer").getObjects().remove(o));

        TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get("pathLayer");

        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                markers[i][j] = false;
                layer.setCell(i, j, null);
            }
        }
    }
}
