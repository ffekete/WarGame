package com.mygdx.wargame.battle.bullet;

import com.badlogic.gdx.assets.AssetManager;
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
        texture.setRegion(0,0, 32, 32);
        batch.draw(texture, getX(), getY(), 16, 16, 32, 32, 1f, 1f, getRotation());
    }
}
