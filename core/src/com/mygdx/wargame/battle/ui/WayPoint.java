package com.mygdx.wargame.battle.ui;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Pool;
import com.mygdx.wargame.battle.screen.StageElementsStorage;
import com.mygdx.wargame.config.Config;
import com.mygdx.wargame.util.StageUtils;

public class WayPoint extends Actor implements Pool.Poolable {
    private AssetManager assetManager;
    private Texture texture;
    private StageElementsStorage stageElementsStorage;

    public WayPoint(AssetManager assetManager, StageElementsStorage stageElementsStorage) {
        this.assetManager = assetManager;
        this.texture = assetManager.get("WayPoint.png", Texture.class);
        this.stageElementsStorage = stageElementsStorage;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(Color.valueOf("FFFFFF55"));
        batch.draw(texture, getX(), getY() - 0.3f * Config.UI_SCALING, 0.5f, 0.5f, 1, 1, 0.5f, 0.5f, getRotation(), 0, 0, 48, 48, false, false);
        batch.setColor(Color.WHITE);
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
    }

    @Override
    public void reset() {
        this.setPosition(0,0);
        this.texture = assetManager.get("WayPoint.png", Texture.class);
    }
}
