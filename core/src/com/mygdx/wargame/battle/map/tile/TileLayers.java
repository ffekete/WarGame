package com.mygdx.wargame.battle.map.tile;

public class TileLayers {

    private Class<? extends Tile> groundTile;
    private Class<? extends Tile> foliageTile;

    public TileLayers(Class<? extends Tile> groundTile, Class<? extends Tile> foliageTile) {
        this.groundTile = groundTile;
        this.foliageTile = foliageTile;
    }

    public Class<? extends Tile> getGroundTile() {
        return groundTile;
    }

    public Class<? extends Tile> getFoliageTile() {
        return foliageTile;
    }
}
