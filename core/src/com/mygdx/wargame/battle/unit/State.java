package com.mygdx.wargame.battle.unit;

public enum State {
    Walk(1, 5), Attack(5, 8), Idle(0, 1), Dead(8, 11);

    private int start;
    private int end;


    State(int col, int end) {
        this.start = col;
        this.end = end;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }
}
