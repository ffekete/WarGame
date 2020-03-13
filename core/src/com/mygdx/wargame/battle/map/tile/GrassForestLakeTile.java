package com.mygdx.wargame.battle.map.tile;

public class GrassForestLakeTile implements Tile {

    private TileState tileState = TileState.Intact;

    @Override
    public String getPath() {
        return "tiles/GrassBigForestLake.png";
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
}
