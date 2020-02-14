package com.mygdx.wargame.battle.action;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.mygdx.wargame.battle.lock.ActionLock;
import com.mygdx.wargame.battle.screen.StageElementsStorage;
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

        label = new Label(message, labelStyle);

        Vector2 newCoordinates = StageUtils.convertBetweenStages(stageElementsStorage.stage, stageElementsStorage.hudStage, initialX, initialY);
        label.setPosition(newCoordinates.x, newCoordinates.y);

        actionLock.setWaitForObject(label);
    }

    public boolean act(float delta) {
        super.act(delta);
        if (isComplete()) {
            System.out.println("Completed: " + label.getText());
            actionLock.removeWaitingObject(label);
            stageElementsStorage.hudStage.getActors().removeValue(label, true);
        }
        return isComplete();
    }

    @Override
    protected void update(float percent) {
        System.out.println(this.getActor().toString());
        System.out.println("percent: " + percent);
        if (firstRun) {
            stageElementsStorage.hudStage.addActor(label);
            System.out.println("Created: " + label.getText());
            firstRun = false;
        }
        System.out.println(" ----------------------------- ");
        label.setY(label.getY() + percent);
    }
}
