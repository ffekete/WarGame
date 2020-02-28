package com.mygdx.wargame.battle.map.tileselector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.wargame.battle.map.MapUtils;
import com.mygdx.wargame.battle.map.NodeGraph;
import com.mygdx.wargame.battle.map.overlay.TileOverlayType;

public class WaterTileSelector implements Selector {

    private TextureRegion textureRegion;
    private AssetManager assetManager;
    private int step = 0;
    private float delay = 0f;

    public WaterTileSelector(AssetManager assetManager) {
        this.assetManager = assetManager;
        textureRegion = new TextureRegion(this.assetManager.get("tileset/Water.png", Texture.class));
    }

    public TextureRegion getFor(NodeGraph map, int x, int y, int skip) {

        delay += Gdx.graphics.getDeltaTime();

        if(delay >= 0.1) {
            delay = 0;
            step = (step + 1) % 2;
        }

        int mask = MapUtils.bitmask4bitForTile(map, x, y, TileOverlayType.Water, skip);

        // rock
        textureRegion.setRegion((mask % 4 + step) * 16, (mask / 4) * 16, 16, 16);
        return textureRegion;
    }

}