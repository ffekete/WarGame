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

    public AnimatedDrawable(TextureRegion textureRegion, float speed, int idle) {
        super(getTextureRegion(textureRegion));
        this.idle = idle;
        this.speed = speed;

        this.speed = speed;
        size = textureRegion.getTexture().getWidth() / 32;
        this.idle = idle;
        this.idleCounter = new Random().nextInt(idle);
        this.textureRegion = textureRegion;
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

        textureRegion.setRegion(step * 32, 0, 32, 16);
        batch.draw(textureRegion, x, y, width, height);
    }

    private static TextureRegion getTextureRegion(TextureRegion texture) {
        texture.setRegion(0, 0, 32, 16);
        return texture;
    }
}
