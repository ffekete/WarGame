package com.mygdx.wargame.battle.action;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.mygdx.wargame.battle.lock.ActionLock;
import com.mygdx.wargame.battle.screen.StageElementsStorage;

public class CenterCameraAction extends TemporalAction {

    private float startX;
    private float startY;
    private float endX;
    private float endY;
    private StageElementsStorage stageElementsStorage;

    private boolean firstRun = true;
    private boolean finished = false;

    public CenterCameraAction(StageElementsStorage stageElementsStorage, ActionLock actionLock) {
        this.stageElementsStorage = stageElementsStorage;
        this.setDuration(1);
    }

    @Override
    protected void update(float percent) {
        float x, y;
        if (firstRun) {
            x = startX;
            y = startY;
            //actionLock.setLocked(true);
            firstRun = false;
        } else if (percent >= 0.95 && !finished) {
            x = endX;
            y = endY;
            //actionLock.setLocked(false);
            finished = true;
        } else {
            x = startX + (endX - startX) * percent;
            y = startY + (endY - startY) * percent;
        }

        stageElementsStorage.stage.getCamera().position.x = x;
        stageElementsStorage.stage.getCamera().position.y = y;
    }

    public void setStartPosition(float x, float y) {
        startX = x;
        startY = y;
    }

    public void setPosition(float x, float y) {
        endX = x;
        endY = y;
    }
}

