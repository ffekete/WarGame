package com.mygdx.wargame.battle.bullet;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class IonBullet extends AbstractBullet {

    private TextureRegion texture;
    private float delay = 0f;
    private int col = 0;

    public IonBullet(AssetManager assetManager) {
        this.texture = new TextureRegion(assetManager.get("Ion.png", Texture.class));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(Color.WHITE);
        delay += 0.1f;
        if(delay == 0.2) {
            delay = 0;
            col++;
            if(col == 5) {
                col = 0;
            }
        }
        texture.setRegion(col * 32,0, 32, 32);
        batch.draw(texture, getX(), getY(), 0.5f, 0.5f, 1, 1, 1f, 1f, getRotation());
    }
}
