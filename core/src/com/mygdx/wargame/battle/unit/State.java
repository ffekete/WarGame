package com.mygdx.wargame.battle.unit;

public enum State {
    Walk(2), Attack(3), Idle(0);

    private int col;

    State(int col) {
        this.col = col;
    }

    public int getCol() {
        return col;
    }
}
