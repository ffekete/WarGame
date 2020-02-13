package com.mygdx.wargame.battle.map;

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
