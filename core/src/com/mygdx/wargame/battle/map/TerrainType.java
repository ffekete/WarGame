package com.mygdx.wargame.battle.map;

import com.mygdx.wargame.battle.map.tile.TileSets;

public enum TerrainType {
    Jungle(-2, 5, -10, TileSets.Jungle),
    Desert(-1, 0, -20, TileSets.Desert),
    Swamp(-2, 10, -5, TileSets.Swamp),
    Grassland(0, 0, 0, TileSets.GrassLand),
    Snow(-1, 0, 20, TileSets.Snow);

    private int movementModifier;
    private int stabilityModifier;
    private int heatModifier;
    private TileSets tileSets;

    TerrainType(int movementModifier, int stabilityModifier, int heatModifier, TileSets tileSets) {
        this.movementModifier = movementModifier;
        this.stabilityModifier = stabilityModifier;
        this.heatModifier = heatModifier;
        this.tileSets = tileSets;
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

    public TileSets getTileSets() {
        return tileSets;
    }
}
