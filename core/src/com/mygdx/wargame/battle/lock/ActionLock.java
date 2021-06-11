package com.mygdx.wargame.battle.lock;

import java.util.ArrayList;
import java.util.List;

public class ActionLock {

    private boolean isLocked = false;
    private List<Object> waitForObject = new ArrayList<>();

    public boolean isLocked() {
        return isLocked || isWaitForObject();
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    public boolean isWaitForObject() {
        return !waitForObject.isEmpty();
    }

    public void setWaitForObject(Object object) {
        this.waitForObject.add(object);
    }

    public void removeWaitingObject(Object object) {
        this.waitForObject.remove(object);
    }
}
