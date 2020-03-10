package com.mygdx.wargame.battle.map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.mygdx.wargame.battle.map.tile.Tile;
import com.mygdx.wargame.battle.map.tile.TileSets;
import com.mygdx.wargame.battle.screen.AssetManagerLoaderV2;

import java.util.Random;

public class TiledMapGenerator {

    private AssetManagerLoaderV2 assetManagerLoaderV2;

    public TiledMapGenerator(AssetManagerLoaderV2 assetManagerLoaderV2) {
        this.assetManagerLoaderV2 = assetManagerLoaderV2;
    }

    public TiledMap generate(int width, int height, TileSets tileSet) {
        TiledMap tiledMap = new TiledMap();

        TiledMapTileLayer groundLayer = new TiledMapTileLayer(width, height, 32, 16);
        groundLayer.setName("groundLayer");


        tiledMap.getLayers().add(groundLayer);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();

                Tile path = null;
                try {
                    path = ((Class<? extends Tile>) tileSet.getTexturePaths().toArray()[new Random().nextInt(tileSet.getTexturePaths().size())]).newInstance();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

                cell.setTile(new StaticTiledMapTile(new TextureRegion(assetManagerLoaderV2.getAssetManager().get(path.getPath(), Texture.class))));
                TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get("groundLayer");
                layer.setCell(i, j, cell);
            }
        }

        return tiledMap;
    }

}
