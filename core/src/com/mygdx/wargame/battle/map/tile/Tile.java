package com.mygdx.wargame.battle.map.tile;

public interface Tile {
    String getPath();
    String getDestroyedPath();
    boolean canBeDestroyed();
    TileState getTileState();
    void setTileState(TileState tileState);
}
