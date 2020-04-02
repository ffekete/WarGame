package com.mygdx.wargame.battle.map.tile;

public interface Tile {
    String getPath();
    String getDestroyedPath();
    boolean canBeDestroyed();
    TileState getTileState();
    void setTileState(TileState tileState);
    boolean isImpassable();
    int getAppeal();
    String getDescription();

    int getTileWorldHeight();
    int getRangeModifier();
    int getHitChanceModifierForAttackers();
    int getHitChanceModifierForDefenders();
    int getStabilityModifier();
    int getHeatDissipationModifier();
}
