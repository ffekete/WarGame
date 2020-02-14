package com.mygdx.wargame.battle.map;

public enum LayerIndex {
    Ground(0),
    Tiles(1),
    Decoration(2);

    private int index;

    LayerIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
