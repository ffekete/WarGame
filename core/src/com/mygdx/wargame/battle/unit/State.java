package com.mygdx.wargame.battle.unit;

public enum State {
    Walk(0, 1), Attack(0, 1), Idle(0, 1), Dead(0, 1), Jump(0, 1), Crouch(0, 1);

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
