package com.mygdx.wargame.battle.map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Node extends Actor {

    private Node parent;
    private int tile;
    float effort;
    float f, g, h;
    private ShapeRenderer shapeRenderer;

    @Override
    public boolean equals(Object obj) {
        if (!this.getClass().equals(obj.getClass())) {
            return false;
        }
        return ((Node) obj).getX() == getX() && ((Node) obj).getY() == getY() && effort == ((Node) obj).effort;
    }

    @Override
    public int hashCode() {
        int hash = 31 * (int)getX();
        hash = 31 * hash + Float.hashCode(effort);
        return 31 * hash + (int)getY();
    }

    public Node(int tile, float x, float y, float effort, ShapeRenderer shapeRenderer) {
        this.tile = tile;
        this.setX(x);
        this.setY(y);
        this.effort = effort;
        this.shapeRenderer = shapeRenderer;
    }

    public int getTile() {
        return tile;
    }

    public Node getParentNode() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public void setTile(int tile) {
        this.tile = tile;
    }

    public void setEffort(float effort) {
        this.effort = effort;
    }

    public void setF(float f) {
        this.f = f;
    }

    public void setG(float g) {
        this.g = g;
    }

    public void setH(float h) {
        this.h = h;
    }

    public float getEffort() {
        return effort;
    }

    public float getF() {
        return f;
    }

    public float getG() {
        return g;
    }

    public float getH() {
        return h;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        shapeRenderer.setColor(Color.valueOf("44440033"));
        shapeRenderer.circle(getX(), getY(), 1);
    }
}