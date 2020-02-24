package com.mygdx.wargame.battle.map.fire;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.Random;

public class FireEffect extends Actor {

    private TextureRegion textureRegion;

    private int step = new Random().nextInt(4);
    private float counter = 0;

    public FireEffect(AssetManager assetManager, float x, float y) {
        textureRegion = new TextureRegion(assetManager.get("Flame.png", Texture.class));
        setPosition(x, y);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        counter += Gdx.graphics.getDeltaTime();

        if(counter >= 0.1f) {
            step = (step + 1) % 4;
            counter = 0;
        }

        batch.setColor(Color.valueOf("FFFFFFAA"));
        textureRegion.setRegion(step * 48, 0 , 48 ,48);
        batch.draw(textureRegion, getX(), getY(), 1, 1);
    }
}
