package com.mygdx.wargame.battle.screen;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class IsometricSprite extends Actor {

    private IsoUtils isoUtils = new IsoUtils();
    private Sprite sprite;
    private Camera camera;

    public IsometricSprite(Texture texture) {
        super();
        this.sprite = new Sprite(texture);
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
        Vector2 v2 = isoUtils.worldToScreen(getX(), getY());
        batch.draw(sprite.getTexture(), v2.x + 10, v2.y + 20);
    }
}
