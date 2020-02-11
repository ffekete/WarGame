package com.mygdx.wargame.battle.bullet;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class PlasmaBullet extends AbstractBullet {

    private TextureRegion texture;
    private float delay = 0f;
    private int col = 0;

    public PlasmaBullet(AssetManager assetManager) {
        this.texture = new TextureRegion(assetManager.get("PlasmaBullet.png", Texture.class));
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
        batch.draw(texture, getX(), getY(), 32, 32);
    }
}
