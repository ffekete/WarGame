package com.mygdx.wargame.battle.unit;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.mygdx.wargame.battle.controller.SelectionController;

public class Scout extends AbstractMech {

    private SpriteBatch spriteBatch;
    private SelectionController selectionController;
    private String name;

    public Scout(String name, SpriteBatch spriteBatch, SelectionController selectionController, AssetManager assetManager) {
        this.spriteBatch = spriteBatch;
        this.selectionController = selectionController;
        this.name = name;

        setTouchable(Touchable.enabled);
        setSize(4, 4);
        this.textureRegion = new TextureRegion(assetManager.get("Maverick.png", Texture.class), 32, 0, 32, 32);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(getX(), getY(), spriteBatch, selectionController, textureRegion);
    }
}
