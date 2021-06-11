package com.mygdx.wargame.battle.map.tile;

public class GrassTileLowLand implements Tile {

    @Override
    public String getPath() {
        return "tiles/GrassBigLow.png";
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
        return -1;
    }

    @Override
    public String getDescription() {
        return "Lower lying tile. \n-1 to attack range for all \nweapons for non flying units.";
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
        return "Grass lowland";
    }
}
