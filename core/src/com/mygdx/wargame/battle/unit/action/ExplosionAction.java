package com.mygdx.wargame.battle.unit.action;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ExplosionAction extends Actor {

    private double counter = 0;
    private int duration = 0;
    private SpriteBatch spriteBatch;
    private TextureRegion textureRegion;
    private AssetManager assetManager;
    private int slow = 0;
    private int step = 0;
    private int x, y;

    public ExplosionAction(SpriteBatch spriteBatch, AssetManager assetManager, int x, int y) {
        this.spriteBatch = spriteBatch;
        this.assetManager = assetManager;
        this.textureRegion = new TextureRegion(this.assetManager.get("Marauder.png", Texture.class), 32, 0, 32, 32);
        this.x = x;
        this.y = y;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        draw(x, y, spriteBatch, textureRegion);
    }

    public void draw(float x, float y, SpriteBatch spriteBatch, TextureRegion texture) {

        if (slow == 0) {
            slow++;
            step++;
            if (step == 5) step = 1;
        } else {
            slow++;
            if (slow == 5)
                slow = 0;
        }

        texture.setRegion(step * 32, 6 * 32, 32, 32);
        spriteBatch.draw(texture, x - 0.5f, y, 2, 2);
    }

    @Override
    public void act(float delta) {

        counter += delta;

        if (counter > 0.15f) {
            duration++;
            counter = 0.0f;


        }
        if (duration == 3)
            getStage().getActors().removeValue(this, true);
    }
}
