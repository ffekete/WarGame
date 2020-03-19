package com.mygdx.wargame.battle.screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class IsometricAnimatedSprite extends Actor {

    private IsoUtils isoUtils = new IsoUtils();
    private TextureRegion textureRegion;
    private int step = 0;
    private int counter = 0;
    private int nrOfSteps;

    public IsometricAnimatedSprite(Texture texture) {
        super();
        this.textureRegion = new Sprite(texture);
        this.nrOfSteps = texture.getWidth() / 64;
    }

    @Override
    public void setPosition(float x, float y) {
        super.setX(x);
        super.setY(y);
    }

    @Override
    public void setPosition(float x, float y, int alignment) {
        this.setPosition(x, y);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        counter++;
        if(counter >= 60) {
            step = (step + 1) % nrOfSteps;
            counter = 0;

        }

        Vector2 v2 = isoUtils.worldToScreen(getX(), getY());
        textureRegion.setRegion(0 + step * 64, 0, 64, 64);
        batch.draw(textureRegion, v2.x + 40, v2.y + 20, 32, 32, 64, 64, 1, 1, getRotation());
    }
}
