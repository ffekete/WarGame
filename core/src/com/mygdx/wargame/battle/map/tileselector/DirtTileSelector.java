package com.mygdx.wargame.battle.map.tileselector;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.wargame.battle.map.MapUtils;
import com.mygdx.wargame.battle.map.NodeGraph;
import com.mygdx.wargame.battle.map.overlay.TileOverlayType;

public class DirtTileSelector implements Selector {

    private TextureRegion textureRegion;
    private AssetManager assetManager;

    public DirtTileSelector(AssetManager assetManager) {
        this.assetManager = assetManager;
        textureRegion = new TextureRegion(this.assetManager.get("tileset/Dirt.png", Texture.class));
    }

    public TextureRegion getFor(NodeGraph map, int x, int y, int skip) {

        int mask = MapUtils.bitmask4bitForTile(map, x,y, TileOverlayType.Dirt, skip);

        // rock
        textureRegion.setRegion((mask % 4) * 32, (mask / 4) * 32, 32, 32);
        return textureRegion;
    }

}