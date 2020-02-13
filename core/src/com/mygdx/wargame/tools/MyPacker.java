package com.mygdx.wargame.tools;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;
public class MyPacker {
	public static void main (String[] args) throws Exception {
		TexturePacker.Settings settings = new TexturePacker.Settings();
		settings.maxWidth = 2048;
		settings.maxHeight = 2048;

		TexturePacker.process(settings, "core/assets/", "core/assets/packed", "main.png");
	}
}