package com.mygdx.wargame.battle.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.mygdx.wargame.battle.lock.ActionLock;
import com.mygdx.wargame.mech.Mech;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class BattleMap {

    int width, height;
    private NodeGraph nodeGraphLv1;
    private ActionLock actionLock;
    private TerrainType terrainType;
    private AssetManager assetManager;
    private TiledMap tiledMap;
    private TextureRegionSelector textureRegionSelector;
    private int unitSize;
    private int[][] fireMap;

    private Map<Mech, List<Node>> paths = new HashMap<>();

    public BattleMap(int x, int y, ActionLock actionLock, TerrainType terrainType, AssetManager assetManager, TextureRegionSelector textureRegionSelector, int unitSize) {
        this.width = x;
        this.height = y;

        fireMap = new int[x][y];

        this.actionLock = actionLock;
        this.terrainType = terrainType;
        this.assetManager = assetManager;
        this.textureRegionSelector = textureRegionSelector;
        this.unitSize = unitSize;

        tiledMap = new TiledMap();
        MapLayers layers = tiledMap.getLayers();

        layers.add(new TiledMapTileLayer(BattleMapConfig.WIDTH, BattleMapConfig.HEIGHT, unitSize, unitSize));
        layers.add(new TiledMapTileLayer(BattleMapConfig.WIDTH, BattleMapConfig.HEIGHT, unitSize, unitSize));
        layers.add(new TiledMapTileLayer(BattleMapConfig.WIDTH, BattleMapConfig.HEIGHT, unitSize, unitSize));

        this.nodeGraphLv1 = new NodeGraph(width, height);

        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                Node node;

                if (nodeGraphLv1.getNodeWeb()[i][j] == null) {
                    node = new Node(i, j);
                    TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
                    cell.setTile(new StaticTiledMapTile(this.textureRegionSelector.select(terrainType)));
                    getLayer(LayerIndex.Ground).setCell(i, j, cell);

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
        //System.out.println("Done.");
    }

    private void addNodeIfDoesntExists(Node node, int i, int j, NodeGraph nodeGraph) {
        if (i < 0 || i >= width || j < 0 || j >= height) {
            return;
        }

        Node newNode;
        if (nodeGraph.getNodeWeb()[i][j] == null) {
            newNode = new Node(i, j);
            TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
            cell.setTile(new StaticTiledMapTile(textureRegionSelector.select(terrainType)));
            TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get(LayerIndex.Ground.getIndex());
            layer.setCell(i, j, cell);
            nodeGraph.addNode(newNode);
        } else {
            newNode = nodeGraph.getNodeWeb()[i][j];
        }

        nodeGraph.connectCities(node, newNode);
        //nodeGraph.connectCities(newNode, node);
    }

    public GraphPath<Node> calculatePath(Node s, Node g) {
        return nodeGraphLv1.findPath(s, g);
    }

    public void addPath(Mech man, GraphPath<Node> path) {
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

    public List<Node> getPath(Mech abstractMech) {
        return paths.get(abstractMech);
    }

    public NodeGraph getNodeGraphLv1() {
        return nodeGraphLv1;
    }

    public TerrainType getTerrainType() {
        return terrainType;
    }

    public void removePath(Mech key) {
        paths.computeIfAbsent(key, v -> new ArrayList<>());
        paths.get(key).clear();
    }

    public TiledMap getTiledMap() {
        return tiledMap;
    }

    public TiledMapTileLayer getLayer(LayerIndex layerIndex) {
        return (TiledMapTileLayer) tiledMap.getLayers().get(layerIndex.getIndex());
    }

    public static class TextureRegionSelector {

        private AssetManager assetManager;

        public TextureRegionSelector(AssetManager assetManager) {
            this.assetManager = assetManager;
        }

        public TextureRegion select(TerrainType terrainType) {
            return new TextureRegion(assetManager.get("Grass.png", Texture.class));
        }
    }

    public int[][] getFireMap() {
        return fireMap;
    }
}
