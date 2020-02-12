package com.mygdx.wargame.battle.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;

import static com.mygdx.wargame.config.Config.SCREEN_SIZE_X;
import static com.mygdx.wargame.config.Config.SCREEN_SIZE_Y;

public class BasicMouseHandlingInputAdapter extends InputAdapter {

    private ScreenConfiguration screenConfiguration;

    public BasicMouseHandlingInputAdapter(ScreenConfiguration screenConfiguration) {
        this.screenConfiguration = screenConfiguration;
    }

    @Override
    public boolean scrolled(int amount) {
       screenConfiguration.zoom = amount * Gdx.graphics.getDeltaTime();
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        if (screenY <= 50) {
            screenConfiguration.scrollY = 10 * Gdx.graphics.getDeltaTime();
        }
        else if (screenY >= SCREEN_SIZE_Y - 50) {
            screenConfiguration.scrollY = -10 * Gdx.graphics.getDeltaTime();

        } else {
            screenConfiguration.scrollY = 0;
        }

        if (screenX <= 50) {
            screenConfiguration.scrollX = -10 * Gdx.graphics.getDeltaTime();
        }
        else if (screenX >= SCREEN_SIZE_X - 50) {
            screenConfiguration.scrollX = 10 * Gdx.graphics.getDeltaTime();

        } else {
            screenConfiguration.scrollX = 0;
        }

        return false;
    }

}

