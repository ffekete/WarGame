package com.mygdx.wargame.battle.map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.mygdx.wargame.battle.map.decoration.AnimatedDrawableTile;
import com.mygdx.wargame.battle.map.decoration.AnimatedTiledMapTile;
import com.mygdx.wargame.battle.map.tile.Tile;
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

        TiledMapTileLayer pathLayer = new TiledMapTileLayer(width, height, IsoUtils.TILE_WIDTH, IsoUtils.TILE_HEIGHT);
        pathLayer.setName("pathLayer");
        tiledMap.getLayers().add(pathLayer);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();

                Tile tile = null;
                try {
                    tile = ((Class<? extends Tile>) tileSet.getTexturePaths().toArray()[new Random().nextInt(tileSet.getTexturePaths().size())]).newInstance();
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
            }
        }

        return tiledMap;
    }

}
