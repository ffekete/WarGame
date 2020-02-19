package com.mygdx.wargame.battle.ui;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Pool;
import com.mygdx.wargame.battle.screen.StageElementsStorage;
import com.mygdx.wargame.battle.screen.ui.ScalableLabel;
import com.mygdx.wargame.config.Config;
import com.mygdx.wargame.util.StageUtils;

public class MovementMarker extends Actor implements Pool.Poolable {
    private AssetManager assetManager;
    private Texture texture;
    private Label label;
    private Label.LabelStyle labelStyle;
    private StageElementsStorage stageElementsStorage;

    public MovementMarker(AssetManager assetManager, Label.LabelStyle labelStyle, StageElementsStorage stageElementsStorage) {
        this.assetManager = assetManager;
        this.texture = assetManager.get("MovementMarker.png", Texture.class);
        this.labelStyle = labelStyle;
        this.stageElementsStorage = stageElementsStorage;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(Color.valueOf("FFFFFF22"));
        batch.draw(texture, getX(), getY() - 0.2f * Config.UI_SCALING, 1, 1);
        batch.setColor(Color.WHITE);
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
    }

    public void setLabel(Integer movementPointsCost) {
        this.label = new ScalableLabel("" + movementPointsCost, labelStyle, 0.015f);

        label.setPosition(getX() + 0.5f, getY() + 0.4f);
        label.setColor(Color.valueOf("FFFFFF22"));

        label.setSize(10 * Config.UI_SCALING, 10 * Config.UI_SCALING);
        stageElementsStorage.groundLevel.addActor(label);
    }


    @Override
    public void reset() {
        this.setPosition(0,0);
        stageElementsStorage.groundLevel.removeActor(label);
        this.label = null;
        this.texture = assetManager.get("MovementMarker.png", Texture.class);
    }
}
