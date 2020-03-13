package com.mygdx.wargame.battle.map.tile;

public class GrassPowerPlantTile implements Tile {

    private TileState tileState = TileState.Destroyed;

    @Override
    public String getPath() {
        return "tiles/GrassBigPowerPlant.png";
    }

    @Override
    public String getDestroyedPath() {
        return "tiles/GrassBigPowerPlantDestroyed.png";
    }

    @Override
    public boolean canBeDestroyed() {
        return true;
    }

    @Override
    public TileState getTileState() {
        return tileState;
    }

    @Override
    public void setTileState(TileState tileState) {
        this.tileState = tileState;
    }
}
