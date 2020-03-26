package com.mygdx.wargame.battle.map;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mygdx.wargame.battle.map.decoration.AnimatedDrawableTile;
import com.mygdx.wargame.battle.map.decoration.AnimatedTiledMapTile;
import com.mygdx.wargame.battle.map.tile.Tile;
import com.mygdx.wargame.battle.map.tile.TileLayers;
import com.mygdx.wargame.battle.map.tile.TileSets;
import com.mygdx.wargame.battle.screen.AssetManagerLoaderV2;
import com.mygdx.wargame.battle.screen.IsoUtils;

import java.util.Random;

public class TiledMapGenerator {

    private AssetManagerLoaderV2 assetManagerLoaderV2;

    public TiledMapGenerator(AssetManagerLoaderV2 assetManagerLoaderV2) {
        this.assetManagerLoaderV2 = assetManagerLoaderV2;
    }

    public TiledMap generate(NodeGraph nodeGraph, int width, int height, TileSets tileSet) {
        TiledMap tiledMap = new TiledMap();

        TiledMapTileLayer groundLayer = new TiledMapTileLayer(width, height, IsoUtils.TILE_WIDTH, IsoUtils.TILE_HEIGHT);
        groundLayer.setName("groundLayer");
        tiledMap.getLayers().add(groundLayer);

        TiledMapTileLayer movementMarkersLayer = new TiledMapTileLayer(width, height, IsoUtils.TILE_WIDTH, IsoUtils.TILE_HEIGHT);
        movementMarkersLayer.setName("movementMarkersLayer");
        tiledMap.getLayers().add(movementMarkersLayer);
        movementMarkersLayer.setOpacity(0.3f);

        TiledMapTileLayer directionLayer = new TiledMapTileLayer(width, height, IsoUtils.TILE_WIDTH, IsoUtils.TILE_HEIGHT);
        directionLayer.setName("directionLayer");
        tiledMap.getLayers().add(directionLayer);

        TiledMapTileLayer pathLayer = new TiledMapTileLayer(width, height, IsoUtils.TILE_WIDTH, IsoUtils.TILE_HEIGHT);
        pathLayer.setName("pathLayer");
        tiledMap.getLayers().add(pathLayer);

        TiledMapTileLayer foliageLayer = new TiledMapTileLayer(width, height, IsoUtils.TILE_WIDTH, IsoUtils.TILE_HEIGHT);
        foliageLayer.setName("foliageLayer");
        tiledMap.getLayers().add(foliageLayer);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                TileLayers tileLayers = processGroundTiles(nodeGraph, tileSet, tiledMap, i, j);
                if(tileLayers.getFoliageTile() != null) {
                    processFoliageTiles(nodeGraph, tiledMap, i,j,tileLayers.getFoliageTile());
                }
            }
        }

        return tiledMap;
    }

    private void processFoliageTiles(NodeGraph nodeGraph, TiledMap tiledMap, int i, int j, Class<? extends Tile> foliageTile) {
        TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
        Tile tile = null;
        try {
            tile = foliageTile.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        if(tile != null) {
            cell.setTile(new AnimatedTiledMapTile(new AnimatedDrawableTile(assetManagerLoaderV2.getAssetManager(), tile, 0.1f, 1, IsoUtils.TILE_WIDTH, IsoUtils.TILE_HEIGHT)));
            TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get("foliageLayer");
            if (tile.isImpassable()) {
                nodeGraph.setImpassable(i, j);
            }
            layer.setCell(i, j, cell);
        }
    }

    private TileLayers processGroundTiles(NodeGraph nodeGraph, TileSets tileSet, TiledMap tiledMap, int i, int j) {
        TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
        Tile tile = null;
        TileLayers tileLayers = null;
        try {
            tileLayers = (TileLayers)tileSet.getTexturePaths().toArray()[new Random().nextInt(tileSet.getTexturePaths().size())];
            tile =  tileLayers.getGroundTile().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        cell.setTile(new AnimatedTiledMapTile(new AnimatedDrawableTile(assetManagerLoaderV2.getAssetManager(), tile, 0.1f, 1, IsoUtils.TILE_WIDTH, IsoUtils.TILE_HEIGHT)));
        TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get("groundLayer");
        if(tile.isImpassable()) {
            nodeGraph.setImpassable(i, j);
        }
        layer.setCell(i, j, cell);
        return tileLayers;
    }

}
