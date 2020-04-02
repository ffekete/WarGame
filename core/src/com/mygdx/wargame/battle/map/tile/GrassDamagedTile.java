package com.mygdx.wargame.battle.map.tile;

public class GrassDamagedTile implements Tile {

    @Override
    public String getPath() {
        return "tiles/GrassBigDamaged.png";
    }

    @Override
    public Tile getDestroyedTile() {
        return new GrassTileLowLand();
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
        return "Damaged grass tile. Craters everywhere.";
    }

    @Override
    public int getTileWorldHeight() {
        return 0;
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
