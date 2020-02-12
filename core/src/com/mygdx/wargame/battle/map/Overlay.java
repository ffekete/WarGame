package com.mygdx.wargame.battle.map;

import com.badlogic.gdx.graphics.Texture;

public class Overlay {
    private Texture overlay;
    private TileOverlayType tileOverlayType;

    public Overlay(Texture overlay, TileOverlayType tileOverlayType) {
        this.overlay = overlay;
        this.tileOverlayType = tileOverlayType;
    }

    public Texture getOverlay() {
        return overlay;
    }

    public void setOverlay(Texture overlay) {
        this.overlay = overlay;
    }

    public TileOverlayType getTileOverlayType() {
        return tileOverlayType;
    }

    public void setTileOverlayType(TileOverlayType tileOverlayType) {
        this.tileOverlayType = tileOverlayType;
    }
}
