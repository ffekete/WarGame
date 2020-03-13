package com.mygdx.wargame.battle.map.tile;

public class GrassCityTile implements Tile {

    private TileState tileState = TileState.Intact;

    @Override
    public String getPath() {
        return "tiles/GrassBigCity.png";
    }

    @Override
    public String getDestroyedPath() {
        return "tiles/GrassBigCityDamaged.png";
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

    @Override
    public boolean isImpassable() {
        return false;
    }
}