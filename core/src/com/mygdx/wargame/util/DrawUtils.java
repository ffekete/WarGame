package com.mygdx.wargame.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class DrawUtils {

    public static void clearScreen() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

}
