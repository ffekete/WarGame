package com.mygdx.wargame.battle.ui;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class MovementMarker extends Actor {
    private AssetManager assetManager;
    private Texture texture;

    public MovementMarker(AssetManager assetManager) {
        this.assetManager = assetManager;
        this.texture = assetManager.get("MovementMarker.png", Texture.class);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(Color.valueOf("FFFFFF22"));
        batch.draw(texture, getX(), getY(), 1, 1);
        batch.setColor(Color.WHITE);
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
    }
}
