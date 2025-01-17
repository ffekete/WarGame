package com.mygdx.wargame.battle.screen.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class FontCreator {

    public static BitmapFont getBitmapFont(float size) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("mainmenu/neuropol.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = (int) (size + 5);
        parameter.color = Color.valueOf("FFFFFF");
        BitmapFont font12 = generator.generateFont(parameter); // font size 12 pixels
        generator.dispose();
        return font12;
    }
}
