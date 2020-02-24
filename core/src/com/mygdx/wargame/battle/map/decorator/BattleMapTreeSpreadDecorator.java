package com.mygdx.wargame.battle.map.decorator;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.google.common.collect.ImmutableList;
import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.battle.map.BattleMapConfig;
import com.mygdx.wargame.battle.map.LayerIndex;
import com.mygdx.wargame.battle.map.decoration.TreeImage;
import com.mygdx.wargame.battle.map.overlay.Overlay;
import com.mygdx.wargame.battle.map.overlay.TileOverlayType;
import com.mygdx.wargame.battle.screen.StageElementsStorage;
import com.mygdx.wargame.config.Config;

import java.util.List;
import java.util.Random;

public class BattleMapTreeSpreadDecorator implements Decorator {

    private int deathLimit = 5;
    private int birthLimit = 3;
    private float chanceToStartAlive = 45;
    private AssetManager assetManager;
    private StageElementsStorage stageElementsStorage;

    private List<Texture> treeVariations;


    public BattleMapTreeSpreadDecorator(AssetManager assetManager, StageElementsStorage stageElementsStorage) {
        this.assetManager = assetManager;
        treeVariations = ImmutableList.<Texture>builder()
                .add(assetManager.get("variation/Tree01.png", Texture.class))
                .add(assetManager.get("variation/Tree02.png", Texture.class))
                .add(assetManager.get("variation/Tree03.png", Texture.class))
                .add(assetManager.get("variation/Tree04.png", Texture.class))
                .build();
        this.stageElementsStorage = stageElementsStorage;
    }

    public void decorate(int step, BattleMap worldMap) {

        int[][] newMap = create(step);

        for (int i = 0; i < newMap.length; i++) {
            for (int j = 0; j < newMap[0].length; j++) {
                if (newMap[i][j] == 1) {

                    for(int k = 0; k < new Random().nextInt(Config.treeSpread); k++) {
                        int rnd = new Random().nextInt(treeVariations.size());

                        TreeImage tree = new TreeImage(treeVariations.get(rnd));
                        tree.setPosition(i + new Random().nextFloat(), j+ new Random().nextFloat());
                        tree.setSize(1, 1);
                        stageElementsStorage.mechLevel.addActor(tree);
                    }
                }
            }
        }
    }

    public int[][] create(int steps) {

        long start = System.currentTimeMillis();
        //Create a new map
        int[][] cellmap = new int[BattleMapConfig.WIDTH][BattleMapConfig.HEIGHT];
        //Set up the map with random values
        cellmap = initialiseMap(cellmap);

        //And now update the simulation for a set number of steps
        for (int i = 0; i < steps; i++) {
            cellmap = doSimulationStep(cellmap);
        }

        System.out.println("Elapsed: " + (System.currentTimeMillis() - start) + " ms");

        return cellmap;
    }

    private int[][] initialiseMap(int[][] map) {
        for (int x = 0; x < BattleMapConfig.WIDTH; x++) {
            for (int y = 0; y < BattleMapConfig.HEIGHT; y++) {
                if (new Random().nextInt(100) < chanceToStartAlive) {
                    map[x][y] = 1;
                } else {
                    map[x][y] = 0;
                }
            }
        }
        return map;
    }

    private int countAliveNeighbours(int[][] map, int x, int y) {
        int count = 0;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                int neighbour_x = x + i;
                int neighbour_y = y + j;
                //If we're looking at the middle point
                if (i == 0 && j == 0) {
                    //Do nothing, we don't want to add ourselves in!
                }
                //In case the index we're looking at it off the edge of the map
                else if (neighbour_x < 0 || neighbour_y < 0 || neighbour_x >= map.length || neighbour_y >= map[0].length) {
                    count = count + 1;
                }
                //Otherwise, a normal check of the neighbour
                else if (map[neighbour_x][neighbour_y] == 1) {
                    count = count + 1;
                }
            }
        }
        return count;
    }

    private int[][] doSimulationStep(int[][] oldMap) {
        int[][] newMap = new int[BattleMapConfig.WIDTH][BattleMapConfig.HEIGHT];
        //Loop over each row and column of the map
        for (int x = 1; x < oldMap.length - 1; x++) {
            for (int y = 1; y < oldMap[0].length - 1; y++) {
                int nbs = countAliveNeighbours(oldMap, x, y);
                //The new value is based on our simulation rules
                //First, if a cell is alive but has too few neighbours, kill it.
                if (oldMap[x][y] == 0) {
                    if (nbs < deathLimit) {
                        newMap[x][y] = 0;
                    } else {
                        newMap[x][y] = 1;
                    }
                } //Otherwise, if the cell is dead now, check if it has the right number of neighbours to be 'born'
                else {
                    if (nbs > birthLimit) {
                        newMap[x][y] = 1;
                    } else {
                        newMap[x][y] = 0;
                    }
                }
            }
        }
        return newMap;
    }
}
