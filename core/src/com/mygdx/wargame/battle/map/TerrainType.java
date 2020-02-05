package com.mygdx.wargame.battle.map;

public enum TerrainType {
    Jungle(-2, -5, 10),
    Desert(-1, 0, 20),
    Swamp(-2, -10, 5),
    Grassland(0, 0, 0),
    Snow(-1, 0, -20);

    private int movementModifier;
    private int stabilityModifier;
    private int heatModifier;

    TerrainType(int movementModifier, int stabilityModifier, int heatModifier) {
        this.movementModifier = movementModifier;
        this.stabilityModifier = stabilityModifier;
        this.heatModifier = heatModifier;
    }

    public int getMovementModifier() {
        return movementModifier;
    }

    public int getStabilityModifier() {
        return stabilityModifier;
    }

    public int getHeatModifier() {
        return heatModifier;
    }
}
