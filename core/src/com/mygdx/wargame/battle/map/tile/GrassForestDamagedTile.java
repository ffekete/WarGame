package com.mygdx.wargame.battle.map.tile;

public class GrassForestDamagedTile implements Tile {

    @Override
    public String getPath() {
        return "tiles/GrassBigForestDamaged.png";
    }

    @Override
    public Tile getDestroyedTile() {
        return new GrassDamagedTile();
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
        return "+15% to hit chance \ndefense for non flying units.\n-10 heat dissipation for \nunits on this tile.";
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
        return -15;
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
        return -10;
    }

    @Override
    public String getName() {
        return "Forest [D]";
    }
}
