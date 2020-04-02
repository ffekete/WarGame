package com.mygdx.wargame.battle.map.tile;

public class GrassForestTile implements Tile {

    @Override
    public String getPath() {
        return "tiles/GrassBigForest.png";
    }

    @Override
    public Tile getDestroyedTile() {
        return new GrassForestDamagedTile();
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
        return "+10% to hit chance defense \nfor non flying units.";
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
        return -10;
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
        return "Forest";
    }
}
