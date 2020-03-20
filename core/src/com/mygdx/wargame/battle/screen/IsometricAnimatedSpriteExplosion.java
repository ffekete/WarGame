package com.mygdx.wargame.battle.screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class IsometricAnimatedSpriteExplosion extends Actor {

    private IsoUtils isoUtils = new IsoUtils();
    private TextureRegion textureRegion;
    private int step = 0;
    private int counter = 0;
    private int nrOfSteps;

    public IsometricAnimatedSpriteExplosion(Texture texture) {
        super();
        this.textureRegion = new Sprite(texture);
        this.nrOfSteps = texture.getWidth() / 96;
        System.out.println("Missile steps: " + nrOfSteps);
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
        if(counter >= 4) {
            step = (step + 1) % nrOfSteps;
            counter = 0;

        }

        Vector2 v2 = isoUtils.worldToScreen(getX(), getY());
        textureRegion.setRegion(0 + step * 96, 0, 96, 96);
        batch.draw(textureRegion, v2.x + 32, v2.y, 0, 0, 64, 64, 1, 1, 0);
    }
}
