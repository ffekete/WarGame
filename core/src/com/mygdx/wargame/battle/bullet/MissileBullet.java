package com.mygdx.wargame.battle.bullet;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MissileBullet extends AbstractBullet {

    private TextureRegion texture;
    private float delay = 0f;
    private int col = 0;

    public MissileBullet(AssetManager assetManager) {
        this.texture = new TextureRegion(assetManager.get("Missile.png", Texture.class));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(Color.WHITE);
        delay += 0.05f;
        if (delay == 0.05f) {
            delay = 0;
            col++;
            if (col == 4) {
                col = 0;
            }
        }
        texture.setRegion(col * 32, 0, 32, 32);
        batch.draw(texture, getX(), getY(), 0.5f, 0.5f, 1, 1, 1f, 1f, getRotation());
    }
}
