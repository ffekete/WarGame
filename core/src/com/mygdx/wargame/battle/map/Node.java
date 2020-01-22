package com.mygdx.wargame.battle.map;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Node {

    private int index;
    private float x;
    private float y;

    public Node(float x, float y) {

        this.setX(x);
        this.setY(y);
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }
}