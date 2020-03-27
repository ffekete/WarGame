package com.mygdx.wargame.battle.map.decoration;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class MovementDirectionDrawable extends TextureRegionDrawable {

    private TextureRegion textureRegion;
    private int step = 0;
    private int size = 0;
    private float counter = 0;

    public MovementDirectionDrawable(Texture texture) {
        this.textureRegion = new TextureRegion(texture);
        size = texture.getWidth() / 128;
    }

    @Override
    public TextureRegion getRegion() {
        counter += Gdx.graphics.getDeltaTime();
        if(counter > 0.5f) {
            counter = 0;
            step = (step + 1)% size;
        }

        textureRegion.setRegion(128 * step, 0, 128, 128);
        return textureRegion;
    }
}
