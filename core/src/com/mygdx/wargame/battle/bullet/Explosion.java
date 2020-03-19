package com.mygdx.wargame.battle.bullet;

import box2dLight.RayHandler;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.wargame.battle.screen.IsometricAnimatedSpriteExplosion;

public class Explosion extends Actor {

    private IsometricAnimatedSpriteExplosion isometricAnimatedSprite;
    private float delay = 0f;
    private int col = 0;
    private RayHandler rayHandler;

    public Explosion(AssetManager assetManager) {
        this.isometricAnimatedSprite = new IsometricAnimatedSpriteExplosion(assetManager.get("effects/Explosion.png", Texture.class));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        isometricAnimatedSprite.draw(batch, parentAlpha);
    }
}
