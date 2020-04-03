package com.mygdx.wargame.battle.map.tile;

public class GrassHillsTile implements Tile {

    @Override
    public String getPath() {
        return "tiles/GrassBigHills.png";
    }

    @Override
    public Tile getDestroyedTile() {
        return null;
    }

    @Override
    public boolean isImpassable() {
        return false;
    }

    @Override
    public int getAppeal() {
        return 1;
    }

    @Override
    public String getDescription() {
        return "Hills tile. \n+1 attack range for attackers.";
    }

    @Override
    public int getTileWorldHeight() {
        return 8;
    }

    @Override
    public int getRangeModifier() {
        return 1;
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
        return "Hills";
    }
}

