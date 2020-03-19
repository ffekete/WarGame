package com.mygdx.wargame.battle.bullet;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.wargame.battle.screen.IsometricAnimatedSprite;
import com.mygdx.wargame.battle.screen.IsometricAnimatedSprite96;

public class MissileExplosion extends Actor {

    private IsometricAnimatedSprite96 isometricAnimatedSprite;
    private float delay = 0f;
    private int col = 0;

    public MissileExplosion(AssetManager assetManager) {
        this.isometricAnimatedSprite = new IsometricAnimatedSprite96(assetManager.get("effects/Explosion.png", Texture.class));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(Color.WHITE);
        isometricAnimatedSprite.setPosition(getX(), getY());
        isometricAnimatedSprite.draw(batch, parentAlpha);
    }
}
