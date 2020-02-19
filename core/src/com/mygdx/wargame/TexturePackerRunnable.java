package com.mygdx.wargame;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;

public class TexturePackerRunnable {

    public static void main(String[] args) {
        TexturePacker.Settings settings = new TexturePacker.Settings();
        settings.maxHeight = 2048;
        settings.maxWidth = 2048;

        TexturePacker.process(settings, "core/assets", "core/assets/packed", "textures");
    }
}
