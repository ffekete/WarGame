package com.mygdx.wargame.battle.bullet;

import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class MissileExplosion extends Actor {

    private TextureRegion texture;
    private float delay = 0f;
    private int col = 0;
    private RayHandler rayHandler;

    public MissileExplosion(AssetManager assetManager, RayHandler rayHandler) {
        this.texture = new TextureRegion(assetManager.get("MissileExplosion.png", Texture.class));
        this.rayHandler = rayHandler;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        delay += Gdx.graphics.getDeltaTime();
        if (delay >= 0.05f) {
            delay = 0;
            col++;
            if (col == 10) {
                col = 0;
            }
        }
        texture.setRegion(col * 48, 0, 48, 48);
        batch.setColor(Color.valueOf("FFFFFFAA"));
        batch.draw(texture, getX() - 0.5f, getY() - 0.25f, 2f, 1.5f);
    }
}
