package com.mygdx.wargame.battle.screen.ui.movement;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Pool;
import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.battle.screen.StageElementsStorage;
import com.mygdx.wargame.battle.screen.ui.ScalableLabel;
import com.mygdx.wargame.config.Config;

public class MovementMarker extends Actor implements Pool.Poolable {
    private AssetManager assetManager;
    private Texture texture;
    private Label label;
    private Label.LabelStyle labelStyle;
    private StageElementsStorage stageElementsStorage;
    private BattleMap battleMap;
    private boolean override = false;

    public MovementMarker(AssetManager assetManager, Label.LabelStyle labelStyle, StageElementsStorage stageElementsStorage, BattleMap battleMap) {
        this.assetManager = assetManager;
        this.texture = assetManager.get("MovementMarker.png", Texture.class);
        this.labelStyle = labelStyle;
        this.stageElementsStorage = stageElementsStorage;
        this.battleMap = battleMap;
        this.setTouchable(Touchable.enabled);

        this.addListener(new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                setColor("88");
                override = true;
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                override= false;
            }
        });
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        if(!Config.showMovementMarkers) {
            label.setVisible(false);
            return;
        }

        label.setVisible(true);

        if(!override) {
            setColor("11");
        }

        batch.setColor(this.getColor());

        batch.draw(texture, getX(), getY(), 1, 1);
        batch.setColor(Color.WHITE);
    }

    private void setColor(String alpha) {
        if (battleMap.getNodeGraphLv1().getNodeWeb()[(int) getX()][(int) getY()].getGroundOverlay() != null &&
                battleMap.getNodeGraphLv1().getNodeWeb()[(int) getX()][(int) getY()].getGroundOverlay().getTileOverlayType() != null) {

            if (battleMap.getNodeGraphLv1().getNodeWeb()[(int) getX()][(int) getY()].getGroundOverlay().getTileOverlayType().getStabilityModifier() <= -20) {
                this.setColor(Color.valueOf("FF0000" + alpha));
            } else if (battleMap.getNodeGraphLv1().getNodeWeb()[(int) getX()][(int) getY()].getGroundOverlay().getTileOverlayType().getStabilityModifier() <= -10) {
                this.setColor(Color.valueOf("FFFF00" + alpha));
            } else {
                this.setColor(Color.valueOf("FFFFFF" + alpha));
            }
        } else {
            this.setColor(Color.valueOf("FFFFFF" + alpha));
        }
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
    }

    public void setLabel(Integer movementPointsCost) {
        this.label = new ScalableLabel("" + movementPointsCost, labelStyle, 0.02f);

        label.setPosition(getX() + 0.45f, getY() + 0.6f);
        label.setColor(Color.valueOf("FFFFFF77"));

        label.setSize(1, 1);
        stageElementsStorage.groundLevel.addActor(label);

        label.setTouchable(Touchable.disabled);
    }


    @Override
    public void reset() {
        this.setPosition(0, 0);
        stageElementsStorage.groundLevel.removeActor(label);
        this.label = null;
        this.texture = assetManager.get("MovementMarker.png", Texture.class);
    }
}
