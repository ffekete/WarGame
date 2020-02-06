package com.mygdx.wargame.battle.screen;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class SelectionMarker extends Actor {

    private SpriteBatch spriteBatch;
    private int col = 0;
    private double delay = 0;
    private TextureRegion textureRegion;
    private Color color = Color.valueOf("FFFFFF66");

    public SelectionMarker(AssetManager assetManager, SpriteBatch spriteBatch) {
        this.spriteBatch = spriteBatch;
        textureRegion = new TextureRegion(assetManager.get("SelectionMarker.png", Texture.class));
    }

    @Override
    public void act(float delta) {
        delay += delta;

        if(delay >= 0.25) {
            col++;
            if(col >= 2) {
                col = 0;
            }
            delay = 0;
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        textureRegion.setRegion(col * 32, 0, 32, 32);
        spriteBatch.setColor(color);
        spriteBatch.draw(textureRegion, getX() -1f, getY() + 1f, 3,3);
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }
}
