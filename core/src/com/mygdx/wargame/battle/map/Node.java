package com.mygdx.wargame.battle.map;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Node extends Actor {

    private int index;
    private Texture texture;

    public Node(float x, float y, AssetManager assetManager) {
        this.setX(x);
        this.setY(y);
        this.texture = assetManager.get("DesertTile.png", Texture.class);
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(Color.WHITE);
        batch.draw(texture, getX(), getY(), 1 ,1);
    }
}