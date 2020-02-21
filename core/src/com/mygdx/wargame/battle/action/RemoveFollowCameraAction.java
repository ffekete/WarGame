package com.mygdx.wargame.battle.action;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class RemoveFollowCameraAction extends Action {

    private Actor actor;

    public RemoveFollowCameraAction(Actor actor) {
        this.actor = actor;
    }

    @Override
    public boolean act(float delta) {

        Action action = null;
        for (Action a : actor.getActions()) {
            if (FollowCameraAction.class.isAssignableFrom(a.getClass())) {
                action = a;
            }
        }

        if (action != null) {
            actor.getActions().removeValue(action, true);
        }

        return true;
    }
}

