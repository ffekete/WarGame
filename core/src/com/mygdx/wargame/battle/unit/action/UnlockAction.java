package com.mygdx.wargame.battle.unit.action;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.mygdx.wargame.battle.lock.ActionLock;

public class UnlockAction extends Action {
    private ActionLock actionLock;

    public UnlockAction(ActionLock actionLock) {
        this.actionLock = actionLock;
    }


    @Override
    public boolean act(float delta) {
        actionLock.setLocked(false);
        return true;
    }
}
