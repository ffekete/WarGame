package com.mygdx.wargame.battle.unit.action;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.mygdx.wargame.battle.lock.ActionLock;

public class UnlockAction extends Action {
    private ActionLock actionLock;
    private String msg;

    public UnlockAction(ActionLock actionLock, String msg) {
        this.actionLock = actionLock;
        this.msg = msg;
    }


    @Override
    public boolean act(float delta) {
        actionLock.setLocked(false);
        System.out.println(msg);
        return true;
    }
}
