package com.mygdx.wargame.battle.lock;

public class ActionLock {

    private boolean isLocked = false;

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }
}
