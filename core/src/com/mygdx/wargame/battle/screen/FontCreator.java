package com.mygdx.wargame.battle.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class FontCreator {
    public static BitmapFont getBitmapFont() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("skin/Trench.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 20;
        parameter.color = Color.valueOf("FFFFFF");
        BitmapFont font12 = generator.generateFont(parameter); // font size 12 pixels
        generator.dispose();
        return font12;
    }
}
