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

    @Override
    public int getAppeal() {
        return 0;
    }

    @Override
    public String getDescription() {
        return "Mountain on grass tile. Only aircraft may pass on this tile.";
    }

    @Override
    public int getTileWorldHeight() {
        return 32;
    }

    @Override
    public int getRangeModifier() {
        return -1;
    }

    @Override
    public int getHitChanceModifierForAttackers() {
        return 0;
    }
}

