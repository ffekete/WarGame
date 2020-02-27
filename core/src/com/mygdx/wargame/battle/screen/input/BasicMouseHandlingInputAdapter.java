package com.mygdx.wargame.battle.screen.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.mygdx.wargame.battle.lock.ActionLock;
import com.mygdx.wargame.battle.screen.ScreenConfiguration;

import static com.mygdx.wargame.config.Config.SCREEN_SIZE_X;
import static com.mygdx.wargame.config.Config.SCREEN_SIZE_Y;

public class BasicMouseHandlingInputAdapter extends InputAdapter {

    private ScreenConfiguration screenConfiguration;
    private ActionLock actionLock;

    public BasicMouseHandlingInputAdapter(ScreenConfiguration screenConfiguration, ActionLock actionLock) {
        this.screenConfiguration = screenConfiguration;
        this.actionLock = actionLock;
    }

    @Override
    public boolean scrolled(int amount) {
        screenConfiguration.zoom = amount * Gdx.graphics.getDeltaTime();
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {

        if (actionLock.isLocked())
            return true;

        if (screenY <= 10) {
            screenConfiguration.scrollY = 10 * Gdx.graphics.getDeltaTime();
        } else if (screenY >= SCREEN_SIZE_Y - 10) {
            screenConfiguration.scrollY = -10 * Gdx.graphics.getDeltaTime();

        } else {
            screenConfiguration.scrollY = 0;
        }

        if (screenX <= 10) {
            screenConfiguration.scrollX = -10 * Gdx.graphics.getDeltaTime();
        } else if (screenX >= SCREEN_SIZE_X - 10) {
            screenConfiguration.scrollX = 10 * Gdx.graphics.getDeltaTime();

        } else {
            screenConfiguration.scrollX = 0;
        }

        return false;
    }

}

