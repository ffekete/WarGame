package com.mygdx.wargame.battle.bullet;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Explosion extends Actor {

    private TextureRegion texture;
    private float delay = 0f;
    private int col = 0;

    public Explosion(AssetManager assetManager) {
        this.texture = new TextureRegion(assetManager.get("Explosion.png", Texture.class));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        delay += 0.05f;
        if (delay == 0.10f) {
            delay = 0;
            col++;
            if (col == 12) {
                col = 0;
            }
        }
        texture.setRegion(col * 96, 0, 96, 96);
        batch.setColor(Color.WHITE);
        batch.draw(texture, getX() - 0.25f, getY() - 0.25f, 1.5f, 1.5f);
    }
}
