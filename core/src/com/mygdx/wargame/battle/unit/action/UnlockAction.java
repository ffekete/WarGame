package com.mygdx.wargame.battle.unit.action;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.mygdx.wargame.battle.lock.ActionLock;
import com.mygdx.wargame.battle.rules.facade.GameState;

public class UnlockAction extends Action {
    private ActionLock actionLock;
    private String msg;

    public UnlockAction( String msg) {
        this.actionLock = GameState.actionLock;
        this.msg = msg;
    }


    @Override
    public boolean act(float delta) {
        actionLock.setLocked(false);
        System.out.println(msg);
        return true;
    }
}
