package com.mygdx.wargame.battle.map;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Node extends Actor {

    private int index;

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

    @Override
    public void draw(Batch batch, float parentAlpha) {

    }
}