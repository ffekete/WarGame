package com.mygdx.wargame.battle.action;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class RemoveFollowCameraAction extends Action {

    private FollowCameraAction followCameraAction;

    public RemoveFollowCameraAction(FollowCameraAction followCameraAction) {
        this.followCameraAction = followCameraAction;
    }

    @Override
    public boolean act(float delta) {

        followCameraAction.setFinished(true);
        return true;
    }
}

