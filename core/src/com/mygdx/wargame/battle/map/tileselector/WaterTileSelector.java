package com.mygdx.wargame.battle.map.tileselector;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.wargame.battle.map.MapUtils;
import com.mygdx.wargame.battle.map.NodeGraph;
import com.mygdx.wargame.battle.map.overlay.TileOverlayType;

import java.util.Random;

public class WaterTileSelector implements Selector {

    private TextureRegion textureRegion;
    private AssetManager assetManager;

    public WaterTileSelector(AssetManager assetManager) {
        this.assetManager = assetManager;
        textureRegion = new TextureRegion(this.assetManager.get("tileset/Water.png", Texture.class));
    }

    public TextureRegion getFor(NodeGraph map, int x, int y, int skip, int step) {
        int mask = MapUtils.bitmask4bitForTile(map, x, y, TileOverlayType.Water, skip);

        // rock
        textureRegion.setRegion((mask % 4) * 16 + step * 64, (mask / 4) * 16, 16, 16);
        return textureRegion;
    }

}