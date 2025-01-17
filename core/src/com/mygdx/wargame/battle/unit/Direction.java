package com.mygdx.wargame.battle.unit;

public enum Direction {

    Up(true, 0), Down(false, 0), Left(false, 0), Right(true, 0);

    private boolean mirrored;
    private int offset;

    Direction(boolean mirrored, int offset) {
        this.mirrored = mirrored;
        this.offset = offset;
    }

    public boolean isMirrored() {
        return mirrored;
    }

    public int getOffset() {
        return offset;
    }
}
