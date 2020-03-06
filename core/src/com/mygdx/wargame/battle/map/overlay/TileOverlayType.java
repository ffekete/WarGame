package com.mygdx.wargame.battle.map.overlay;

public enum TileOverlayType {
    Dirt(-20), Water(-10), Crater(-10);

    private int stabilityModifier;

    TileOverlayType(int stabilityModifier) {
        this.stabilityModifier = stabilityModifier;
    }

    public int getStabilityModifier() {
        return stabilityModifier;
    }
}
