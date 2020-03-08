package com.mygdx.wargame.battle.map.decoration;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.Random;

public class AnimatedDrawable extends TextureRegionDrawable {

    private float speed;
    private int idle;
    private int size;
    private int idleCounter = 0;
    private TextureRegion textureRegion;
    private float counter = 0f;
    private int step;
    private int frameWidth;
    private int frameHeight;

    public AnimatedDrawable(TextureRegion textureRegion, float speed, int idle, int width, int height) {
        super(getTextureRegion(textureRegion, width, height));
        this.idle = idle;
        this.speed = speed;

        size = textureRegion.getTexture().getWidth() / width;
        this.idle = idle;
        this.idleCounter = new Random().nextInt(idle);
        this.textureRegion = textureRegion;
        this.frameWidth = width;
        this.frameHeight = height;
    }

    @Override
    public void draw(Batch batch, float x, float y, float width, float height) {
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

        textureRegion.setRegion(step * frameWidth, 0, frameWidth, frameHeight);
        batch.draw(textureRegion, x, y, width, height);
    }

    private static TextureRegion getTextureRegion(TextureRegion texture, int frameWidth, int frameHeight) {
        texture.setRegion(0, 0, frameWidth, frameHeight);
        return texture;
    }

    public void restart() {
        idleCounter = 0;
        step = 0;
    }
}
