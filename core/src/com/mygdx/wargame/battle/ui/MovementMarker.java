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
        batch.draw(texture, getX(), getY(), 1, 1);
        batch.setColor(Color.WHITE);
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
    }

    public void setLabel(Integer movementPointsCost) {
        this.label = new Label("" + movementPointsCost, labelStyle);

        Vector2 newCoord = StageUtils.convertBetweenStages(stageElementsStorage.stage, stageElementsStorage.textStage, getX() + 0.45f, getY() + 0.15f);

        label.setPosition(newCoord.x, newCoord.y);
        label.setColor(Color.valueOf("FFFFFF22"));
        stageElementsStorage.textStage.addActor(label);
    }


    @Override
    public void reset() {
        this.setPosition(0,0);
        stageElementsStorage.textStage.getActors().removeValue(label, true);
        this.label = null;
        this.texture = assetManager.get("MovementMarker.png", Texture.class);
    }
}
