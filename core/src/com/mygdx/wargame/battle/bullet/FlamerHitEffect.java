package com.mygdx.wargame.battle.bullet;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.wargame.battle.screen.IsometricAnimatedSprite;

public class FlamerHitEffect extends AbstractBullet {

    private IsometricAnimatedSprite isometricAnimatedSprite;

    public FlamerHitEffect(AssetManager assetManager) {
        this.isometricAnimatedSprite = new IsometricAnimatedSprite(assetManager.get("bullets/FlamerHit.png", Texture.class), 5);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(Color.WHITE);
        isometricAnimatedSprite.setPosition(getX(), getY());
        isometricAnimatedSprite.setRotation(getRotation() - 45);
        isometricAnimatedSprite.draw(batch, parentAlpha);
    }
}
