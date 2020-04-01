package com.mygdx.wargame.battle.map;

import com.mygdx.wargame.battle.map.tile.Tile;

public class Node {

    private int index;
    private float x, y;
    private boolean impassable = false;
    private int height;
    private Tile tile;

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

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public boolean isImpassable() {
        return impassable;
    }

    public void setImpassable(boolean impassable) {
        this.impassable = impassable;
    }

    public Tile getTile() {
        return tile;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }
}