package com.mygdx.wargame.battle.map.decoration;


import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import static com.mygdx.wargame.config.Config.VIEWPORT_HEIGHT;
import static com.mygdx.wargame.config.Config.VIEWPORT_WIDTH;

public class TreeImage extends Image implements Tree {

    private Texture shadows;

    public TreeImage(Texture texture, AssetManager assetManager) {
        super(texture);
        this.shadows = assetManager.get("Shadow.png", Texture.class);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        if(onScreen()) {
            batch.setColor(Color.valueOf("FFFFFF55"));
            batch.draw(shadows, getX(), getY(), 1, 1);
            batch.setColor(Color.WHITE);
            super.draw(batch, parentAlpha);
        }
    }

    private boolean onScreen() {
        return Math.abs(getStage().getCamera().position.x - getX()) < VIEWPORT_WIDTH / 2 && Math.abs(getStage().getCamera().position.y - getY()) < VIEWPORT_HEIGHT / 2;
    }
}
