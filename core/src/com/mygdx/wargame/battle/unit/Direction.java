package com.mygdx.wargame.battle.unit;

public enum Direction {

    Up(false, 8), Down(false, 16), Left(true, 0), Right(false, 0);

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
