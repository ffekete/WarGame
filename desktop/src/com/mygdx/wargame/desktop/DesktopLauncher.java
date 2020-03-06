package com.mygdx.wargame.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.wargame.WarGame;
import com.mygdx.wargame.common.ScreenRegister;
import com.mygdx.wargame.config.Config;

import static com.mygdx.wargame.config.Config.*;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = SCREEN_SIZE_X;
        config.height = SCREEN_SIZE_Y;
        config.fullscreen = false;

        new LwjglApplication(new WarGame(), config);

        SCREEN_SIZE_X = Gdx.graphics.getDisplayMode(Gdx.graphics.getMonitor()).width;
        SCREEN_SIZE_Y = Gdx.graphics.getDisplayMode(Gdx.graphics.getMonitor()).height;
        System.out.println(HUD_VIEWPORT_WIDTH.get() + " " + HUD_VIEWPORT_HEIGHT.get());
        ScreenRegister.I.getMainMenuScreen().resize(HUD_VIEWPORT_WIDTH.get(), HUD_VIEWPORT_HEIGHT.get());
    }
}
