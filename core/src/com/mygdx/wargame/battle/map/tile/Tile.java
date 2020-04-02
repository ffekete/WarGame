package com.mygdx.wargame.battle.map.tile;

public interface Tile {
    String getPath();
    Tile getDestroyedTile();
    boolean isImpassable();
    int getAppeal();
    String getDescription();

    int getTileWorldHeight();
    int getRangeModifier();
    int getHitChanceModifierForAttackers();
    int getHitChanceModifierForDefenders();
    int getStabilityModifier();
    int getHeatDissipationModifier();
    String getName();
}
