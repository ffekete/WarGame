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
    private int speed;

    public IsometricAnimatedSprite(Texture texture, int speed) {
        super();
        this.textureRegion = new Sprite(texture);
        this.nrOfSteps = texture.getWidth() / 64;
        this.speed = speed;
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

    public void draw(Batch batch, float parentAlpha, float oy) {
        counter++;
        if(counter >= speed) {
            step = (step + 1) % nrOfSteps;
            counter = 0;

        }

        batch.setColor(this.getColor());
        Vector2 v2 = isoUtils.worldToScreen(getX(), getY());
        textureRegion.setRegion(0 + step * 64, 0, 64, 64);

        batch.draw(textureRegion, v2.x + 32, v2.y + 10 + oy, 32, 32, 64, 64, getScaleX(), getScaleY(), getRotation());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        this.draw(batch, parentAlpha, 0);
    }

    public TextureRegion getTextureRegion() {
        return textureRegion;
    }
}
