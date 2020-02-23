package com.mygdx.wargame.battle.action;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.mygdx.wargame.battle.lock.ActionLock;
import com.mygdx.wargame.battle.screen.StageElementsStorage;

public class FollowCameraAction extends Action {

    private Actor actor;
    private boolean finished = false;


    private StageElementsStorage stageElementsStorage;

    public FollowCameraAction(StageElementsStorage stageElementsStorage, Actor actor) {
        this.stageElementsStorage = stageElementsStorage;
        this.actor = actor;
    }

    @Override
    public boolean act(float delta) {
        stageElementsStorage.stage.getCamera().position.x = actor.getX();
        stageElementsStorage.stage.getCamera().position.y = actor.getY();
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }
}

