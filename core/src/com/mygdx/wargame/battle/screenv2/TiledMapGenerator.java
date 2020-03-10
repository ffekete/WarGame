package com.mygdx.wargame.battle.screenv2;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;

public class TiledMapGenerator {

    private AssetManagerLoaderV2 assetManagerLoaderV2;

    public TiledMapGenerator(AssetManagerLoaderV2 assetManagerLoaderV2) {
        this.assetManagerLoaderV2 = assetManagerLoaderV2;
    }

    public TiledMap generate(int width, int height) {
        TiledMap tiledMap = new TiledMap();

        TiledMapTileLayer groundLayer = new TiledMapTileLayer(width, height, 32, 16);
        groundLayer.setName("groundLayer");

        tiledMap.getLayers().add(groundLayer);

        for(int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
                cell.setTile(new StaticTiledMapTile(new TextureRegion(assetManagerLoaderV2.getAssetManager().get("Grass.png", Texture.class))));
                TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get("groundLayer");
                layer.setCell(i, j, cell);
            }
        }

        return tiledMap;
    }

}
