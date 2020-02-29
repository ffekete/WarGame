package com.mygdx.wargame.battle.map.decoration;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;

import java.util.Random;

public class AnimatedImage extends Image implements Tree {

    private int size;
    private int step = 0;
    private float counter = 0;
    private float speed;
    private TextureRegion textureRegion;
    private int idle;
    private int idleCounter = 0;

    public AnimatedImage(TextureRegion texture, float speed, int idle) {
        super(getTextureRegion(texture));
        setAlign(Align.center);
        setSize(16, 16);
        setWidth(16);
        this.textureRegion = texture;
        this.speed = speed;
        size = textureRegion.getTexture().getWidth() / 16;
        this.idle = idle;
        this.idleCounter = new Random().nextInt(idle);
    }

    private static TextureRegion getTextureRegion(TextureRegion texture) {
        texture.setRegion(0, 0, 16, 16);
        return texture;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        counter += Gdx.graphics.getDeltaTime();

        if (idleCounter == 0) {
            if (counter >= speed) {
                step = (step + 1);
                counter = 0;
                if (step == size) {
                    idleCounter++;
                    step = 0;
                }
            }
        } else {
            idleCounter = (idleCounter + 1) % idle;
        }

        textureRegion.setRegion(step * 16, 0, 16, 16);
        batch.draw(textureRegion, getX(), getY(), 16, 16);


    }
}
