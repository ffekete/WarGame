package com.mygdx.wargame.battle.map;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Random;

public class Node {

    private int index;
    private Overlay groundOverlay;
    private float x, y;
    private Overlay decorationOverlay;

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

    public void setDecorationOverlay(Overlay decorationOverlay) {
        this.decorationOverlay = decorationOverlay;
    }

    public void setGroundOverlay(Overlay groundOverlay) {
        this.groundOverlay = groundOverlay;
    }

    public Overlay getGroundOverlay() {
        return groundOverlay;
    }

    public Overlay getDecorationOverlay() {
        return decorationOverlay;
    }
}