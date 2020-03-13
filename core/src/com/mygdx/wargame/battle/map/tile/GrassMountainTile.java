package com.mygdx.wargame.battle.map.tile;

public class GrassMountainTile implements Tile {

    @Override
    public String getPath() {
        return "tiles/GrassBigMountain.png";
    }

    @Override
    public String getDestroyedPath() {
        return null;
    }

    @Override
    public boolean canBeDestroyed() {
        return false;
    }

    @Override
    public TileState getTileState() {
        return TileState.Intact;
    }

    @Override
    public void setTileState(TileState tileState) {

    }

    @Override
    public boolean isImpassable() {
        return true;
    }
}
