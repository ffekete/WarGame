package com.mygdx.wargame.battle.unit.action;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.mygdx.wargame.battle.lock.ActionLock;
import com.mygdx.wargame.battle.rules.facade.GameState;

public class LockAction extends Action {
    private ActionLock actionLock;

    public LockAction() {
        this.actionLock = GameState.actionLock;
    }


    @Override
    public boolean act(float delta) {
        actionLock.setLocked(true);
        return true;
    }
}
