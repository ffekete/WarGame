package com.mygdx.wargame.battle.unit;

public enum State {
    Walk(5, 10), Attack(10, 13), Idle(0, 5), Dead(13, 16), Jump(16, 20), Crouch(16, 17);

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
