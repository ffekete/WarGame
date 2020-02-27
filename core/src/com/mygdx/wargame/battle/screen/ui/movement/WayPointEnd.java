package com.mygdx.wargame.battle.screen.ui.movement;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.wargame.battle.screen.StageElementsStorage;

public class WayPointEnd extends WayPoint {
    private AssetManager assetManager;
    private Texture texture;
    private StageElementsStorage stageElementsStorage;

    public WayPointEnd(AssetManager assetManager, StageElementsStorage stageElementsStorage) {
        super(assetManager, stageElementsStorage);
        this.assetManager = assetManager;
        this.texture = assetManager.get("WayPointEnd.png", Texture.class);
        this.stageElementsStorage = stageElementsStorage;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(Color.valueOf("FFFFFF55"));
        batch.draw(texture, getX(), getY() - 0.3f, 0.5f, 0.5f, 1, 1, 0.5f, 0.5f, getRotation(), 0, 0, 48, 48, false, false);
        batch.setColor(Color.WHITE);
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
    }

    @Override
    public void reset() {
        this.setPosition(0, 0);
        this.texture = assetManager.get("WayPointEnd.png", Texture.class);
    }
}
