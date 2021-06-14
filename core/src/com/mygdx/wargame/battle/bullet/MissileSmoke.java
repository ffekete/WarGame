package com.mygdx.wargame.battle.bullet;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.wargame.battle.screen.IsometricAnimatedSprite;

public class MissileSmoke extends AbstractBullet {

    private IsometricAnimatedSprite isometricAnimatedSprite;

    public MissileSmoke(AssetManager assetManager) {
        this.isometricAnimatedSprite = new IsometricAnimatedSprite(assetManager.get("bullets/Smoke.png", Texture.class), 10);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        isometricAnimatedSprite.setColor(Color.valueOf("FFFFFF99"));
        isometricAnimatedSprite.setPosition(getX(), getY());
        isometricAnimatedSprite.setRotation(getRotation() - 45);
        isometricAnimatedSprite.draw(batch, parentAlpha);
    }
}
