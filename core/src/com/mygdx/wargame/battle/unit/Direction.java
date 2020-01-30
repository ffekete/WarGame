package com.mygdx.wargame.battle.unit;

public enum Direction {

    Up(false), Down(false), Left(true), Right(false);

    private boolean mirrored;

    Direction(boolean mirrored) {
        this.mirrored = mirrored;
    }

    public boolean isMirrored() {
        return mirrored;
    }
}
