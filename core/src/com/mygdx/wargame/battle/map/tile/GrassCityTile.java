package com.mygdx.wargame.battle.map.tile;

public class GrassCityTile implements Tile {

    private TileState tileState = TileState.Intact;

    @Override
    public String getPath() {
        return "tiles/GrassBigBuilding.png";
    }

    @Override
    public String getDestroyedPath() {
        return "tiles/GrassBigBuilding.png";
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
        return true;
    }

    @Override
    public int getAppeal() {
        return 0;
    }

    @Override
    public String getDescription() {
        return "Building on a grassy tile. Nothing special.";
    }

    @Override
    public int getTileWorldHeight() {
        return 32;
    }

    @Override
    public int getRangeModifier() {
        return 0;
    }

    @Override
    public int getHitChanceModifierForAttackers() {
        return 0;
    }

    @Override
    public int getHitChanceModifierForDefenders() {
        return 0;
    }

    @Override
    public int getStabilityModifier() {
        return 0;
    }

    @Override
    public int getHeatDissipationModifier() {
        return 0;
    }
}
