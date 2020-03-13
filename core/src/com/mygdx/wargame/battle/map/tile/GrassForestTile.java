package com.mygdx.wargame.battle.map.tile;

public class GrassForestTile implements Tile {

    private TileState tileState = TileState.Intact;

    @Override
    public String getPath() {
        return "tiles/GrassBigForest.png";
    }

    @Override
    public String getDestroyedPath() {
        return "tiles/GrassBigForestDamaged.png";
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
