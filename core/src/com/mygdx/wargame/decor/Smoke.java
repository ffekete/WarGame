package com.mygdx.wargame.decor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Smoke extends Actor {

    private TextureRegion texture;
    private float delay = 0f;
    private int col = 0;


    public Smoke(AssetManager assetManager, float x, float y) {
        this.texture = new TextureRegion(assetManager.get("Smoke.png", Texture.class));
        setPosition(x, y);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        delay += Gdx.graphics.getDeltaTime();
        if (delay >= 0.15f) {
            delay = 0;
            col++;
            if (col == 3) {
                this.remove();
            }
        }
        texture.setRegion(col * 16, 0, 16, 16);
        batch.setColor(Color.valueOf("FFFFFF66"));
        batch.draw(texture, getX(), getY(),0.5f, 0.5f, 0.5f, 0.5f, 1, 1, 0);
        batch.setColor(Color.valueOf("FFFFFFFF"));
    }

}
