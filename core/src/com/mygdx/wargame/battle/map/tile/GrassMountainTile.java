package com.mygdx.wargame.battle.map.tile;

public class GrassMountainTile implements Tile {

    @Override
    public String getPath() {
        return "tiles/GrassBigMountain.png";
    }

    @Override
    public Tile getDestroyedTile() {
        return null;
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
        return "Mountain on grass tile.\nOnly aircraft may pass on this tile.";
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

    @Override
    public String getName() {
        return "Mountain";
    }
}

