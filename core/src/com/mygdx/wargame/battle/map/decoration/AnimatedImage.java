package com.mygdx.wargame.battle.map.decoration;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.util.Random;

public class AnimatedImage extends Image implements Tree {

    private int size;
    private int step = 0;
    private float counter = 0;
    private float speed;
    private TextureRegion textureRegion;

    public AnimatedImage(TextureRegion texture, float speed) {
        this.textureRegion = texture;
        this.speed = speed;
        size = textureRegion.getRegionWidth() / 48;
        texture.flip(new Random().nextBoolean(), new Random().nextBoolean());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        counter += Gdx.graphics.getDeltaTime();

        if (counter >= speed) {
            step = (step + 1) % size;
            counter = 0;
        }

        textureRegion.setRegion(step * 48, 0, 48, 48);
        batch.draw(textureRegion, getX(), getY(), 1, 1);
    }
}
