package com.mygdx.wargame.battle.bullet;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MachineGunBullet extends AbstractBullet {

    private TextureRegion texture;

    public MachineGunBullet(AssetManager assetManager) {
        this.texture = new TextureRegion(assetManager.get("MachineGun.png", Texture.class));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(Color.WHITE);
        texture.setRegion(0,0, 32, 32);
        batch.draw(texture, getX(), getY(), 0.5f, 0.5f, 1, 1, 1f, 1f, getRotation());
    }
}
