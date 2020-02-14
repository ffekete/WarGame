package com.mygdx.wargame.battle.lock;

import com.badlogic.gdx.scenes.scene2d.ui.Label;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ActionLock {

    private boolean isLocked = false;
    private List<Object> waitForObject = new CopyOnWriteArrayList<>();

    public boolean isLocked() {
        System.out.println("wfo: " + waitForObject.size());
        waitForObject.stream().forEach(o -> System.out.println("    " +((Label)o).getText()));
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
