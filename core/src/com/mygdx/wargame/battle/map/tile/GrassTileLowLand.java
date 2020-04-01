package com.mygdx.wargame.battle.map.tile;

public class GrassTileLowLand implements Tile {

    private TileState tileState = TileState.Intact;

    @Override
    public String getPath() {
        return "tiles/GrassBigLow.png";
    }

    @Override
    public String getDestroyedPath() {
        return "tiles/GrassBigLow.png";
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

    @Override
    public int getAppeal() {
        return -1;
    }

    @Override
    public String getDescription() {
        return "Lower lying tile. -1 to attack range for all weapons.";
    }

    @Override
    public int getTileWorldHeight() {
        return -16;
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
