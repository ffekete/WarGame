package com.mygdx.wargame.battle.map;

import com.badlogic.gdx.assets.AssetManager;
import com.google.common.collect.ImmutableMap;
import com.mygdx.wargame.battle.map.tile.DirtTileSelector;

import java.util.Map;

public class GroundOverlayConfig {
    private Map<TileOverlayType, Selector> selectorMap;
    private AssetManager assetManager;

    public GroundOverlayConfig(AssetManager assetManager) {
        this.assetManager = assetManager;
        this.selectorMap = ImmutableMap.<TileOverlayType, Selector>builder()
                .put(TileOverlayType.Dirt, new DirtTileSelector(this.assetManager))
                .build();
    }

    public Selector getFor(TileOverlayType tileOverlayType) {
        return selectorMap.get(tileOverlayType);
    }
}
