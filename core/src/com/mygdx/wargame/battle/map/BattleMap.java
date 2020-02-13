package com.mygdx.wargame.battle.map;

import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.mygdx.wargame.battle.lock.ActionLock;
import com.mygdx.wargame.battle.screen.localmenu.MechInfoPanelFacade;
import com.mygdx.wargame.mech.Mech;
import com.mygdx.wargame.rules.facade.TurnProcessingFacade;

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
    private TurnProcessingFacade turnProcessingFacade;
    private AssetManager assetManager;
    private MechInfoPanelFacade mechInfoPanelFacade;
    private TiledMap tiledMap;

    private Map<Mech, List<Node>> paths = new HashMap<>();

    public BattleMap(int x, int y, ActionLock actionLock, TerrainType terrainType, TurnProcessingFacade turnProcessingFacade, TurnProcessingFacade turnProcessingFacade1, AssetManager assetManager, MechInfoPanelFacade mechInfoPanelFacade) {
        this.width = x;
        this.height = y;
        this.actionLock = actionLock;
        this.terrainType = terrainType;
        this.turnProcessingFacade = turnProcessingFacade1;
        this.assetManager = assetManager;
        this.mechInfoPanelFacade = mechInfoPanelFacade;

        tiledMap = new TiledMap();
        MapLayers layers = tiledMap.getLayers();

        layers.add(new TiledMapTileLayer(BattleMapConfig.WIDTH, BattleMapConfig.HEIGHT, 32, 32));
        layers.add(new TiledMapTileLayer(BattleMapConfig.WIDTH, BattleMapConfig.HEIGHT, 32, 32));
        layers.add(new TiledMapTileLayer(BattleMapConfig.WIDTH, BattleMapConfig.HEIGHT, 32, 32));

        this.nodeGraphLv1 = new NodeGraph(width, height);

        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                Node node;

                if (nodeGraphLv1.getNodeWeb()[i][j] == null) {
                    node = new Node(i, j);
                    TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
                    cell.setTile(new StaticTiledMapTile(new TextureRegion(assetManager.get("Grassland.png", Texture.class))));
                    getLayer(0).setCell(i, j, cell);

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
            TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
            cell.setTile(new StaticTiledMapTile(new TextureRegion(assetManager.get("Grassland.png", Texture.class))));
            TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get(0);
            layer.setCell(i, j, cell);
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

    public TiledMapTileLayer getLayer(int index) {
        return (TiledMapTileLayer) tiledMap.getLayers().get(index);
    }
}
