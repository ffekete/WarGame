package com.mygdx.wargame.battle.action;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.mygdx.wargame.battle.lock.ActionLock;
import com.mygdx.wargame.battle.screen.StageElementsStorage;
import com.mygdx.wargame.battle.screen.ui.ScalableLabel;
import com.mygdx.wargame.util.StageUtils;

public class ShowMessageActor extends TemporalAction {

    private Label.LabelStyle labelStyle;
    private float initialX, initialY;
    private String message;
    private StageElementsStorage stageElementsStorage;
    private Label label;
    private boolean firstRun = true;
    private ActionLock actionLock;

    public ShowMessageActor(Label.LabelStyle labelStyle, float initialX, float initialY, String message, StageElementsStorage stageElementsStorage, ActionLock actionLock) {
        this.initialX = initialX;
        this.initialY = initialY;
        this.message = message;
        this.labelStyle = labelStyle;
        this.stageElementsStorage = stageElementsStorage;
        this.actionLock = actionLock;

        label = new ScalableLabel(message, labelStyle, 0.02f);

        Vector2 newCoordinates = StageUtils.convertBetweenStages(stageElementsStorage.stage, stageElementsStorage.stage, initialX - 0.5f, initialY);
        label.setPosition(newCoordinates.x, newCoordinates.y);

        actionLock.setWaitForObject(label);
        setDuration(2f);
    }

    public boolean act(float delta) {
        super.act(delta);
        if (isComplete()) {
            actionLock.removeWaitingObject(label);
            stageElementsStorage.airLevel.removeActor(label);
        }
        return isComplete();
    }

    @Override
    protected void update(float percent) {
        if (firstRun) {
            stageElementsStorage.airLevel.addActor(label);
            firstRun = false;
        }
        label.setY(label.getY() + percent / 40f);
    }
}
