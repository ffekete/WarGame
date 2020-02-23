package com.mygdx.wargame.battle.bullet;

import com.badlogic.gdx.Gdx;
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
        delay += Gdx.graphics.getDeltaTime();
        if (delay >= 0.08f) {
            delay = 0;
            col++;
            if (col == 7) {
                col = 0;
            }
        }
        texture.setRegion(col * 48, 0, 48, 48);
        batch.setColor(Color.WHITE);
        batch.draw(texture, getX() - 0.25f, getY() - 0.25f, 1.5f, 1.5f);
    }
}
