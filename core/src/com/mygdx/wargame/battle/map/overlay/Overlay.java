package com.mygdx.wargame.battle.map.overlay;

public class Overlay {
    private TileOverlayType tileOverlayType;

    public Overlay(TileOverlayType tileOverlayType) {
        this.tileOverlayType = tileOverlayType;
    }

    public TileOverlayType getTileOverlayType() {
        return tileOverlayType;
    }

    public void setTileOverlayType(TileOverlayType tileOverlayType) {
        this.tileOverlayType = tileOverlayType;
    }
}
