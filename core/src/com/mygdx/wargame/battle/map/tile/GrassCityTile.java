package com.mygdx.wargame.battle.map.tile;

public class GrassCityTile implements Tile {

    @Override
    public String getPath() {
        return "tiles/GrassBigBuilding.png";
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
        return "Building on a grassy tile.\nNothing special.";
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

    @Override
    public String getName() {
        return "Building";
    }
}
