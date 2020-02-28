package com.mygdx.wargame.decor;

import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Birds extends Actor {

    private TextureRegion texture;
    private float delay = 0f;
    private int col = 0;


    public Birds(AssetManager assetManager, float x, float y) {
        this.texture = new TextureRegion(assetManager.get("Birds.png", Texture.class));
        setPosition(x, y);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        delay += Gdx.graphics.getDeltaTime();
        if (delay >= 0.1f) {
            delay = 0;
            col++;
            if (col == 6) {
                this.remove();
            }
        }
        texture.setRegion(col * 16, 0, 16, 16);
        batch.setColor(Color.WHITE);
        batch.draw(texture, getX(), getY(), 1f, 1f);
    }

}
