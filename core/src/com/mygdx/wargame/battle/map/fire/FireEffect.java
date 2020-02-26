package com.mygdx.wargame.battle.map.fire;

import box2dLight.PointLight;
import box2dLight.RayHandler;
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
    private RayHandler rayHandler;

    private int step = new Random().nextInt(3);
    private float counter = 0;

    public FireEffect(AssetManager assetManager, float x, float y, RayHandler rayHandler) {
        textureRegion = new TextureRegion(assetManager.get("Flame.png", Texture.class));
        this.rayHandler = rayHandler;
        setPosition(x, y);
        new PointLight(rayHandler, 5, new Color(0.5f, 0,0,0.5f), 0.5f, x, y);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        counter += Gdx.graphics.getDeltaTime();

        if(counter >= 0.1f) {
            step = (step + 1) % 3;
            counter = 0;
        }

        batch.setColor(Color.valueOf("FFFFFF66"));
        textureRegion.setRegion(step * 16, 0 , 16 ,16);
        batch.draw(textureRegion, getX(), getY(), 1, 1);
    }
}
