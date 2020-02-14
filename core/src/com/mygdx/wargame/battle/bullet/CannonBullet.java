package com.mygdx.wargame.battle.bullet;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class CannonBullet extends AbstractBullet {

    private TextureRegion texture;
    private float delay = 0f;
    private int col = 0;

    public CannonBullet(AssetManager assetManager) {
        this.texture = new TextureRegion(assetManager.get("CannonBullet.png", Texture.class));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        delay += 0.1f;
        if(delay == 0.2) {
            delay = 0;
            col++;
            if(col == 5) {
                col = 0;
            }
        }
        texture.setRegion(col * 32,0, 32, 32);
        batch.draw(texture, getX(), getY(), 0.5f, 0.5f, 1, 1, 0.5f, 0.5f, getRotation());
    }
}
