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
        if (delay >= 0.07f) {
            delay = 0;
            col++;
            if (col == 10) {
                col = 0;
            }
        }
        texture.setRegion(col * 16, 0, 16, 16);
        batch.setColor(Color.valueOf("FFFFFFCC"));
        batch.draw(texture, getX()-0.25f, getY()-0.25f, 1.5f, 1.5f);
    }
}
