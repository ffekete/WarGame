package com.mygdx.wargame.config;

public class Config {

    public static int SCREEN_SIZE_X = 1366;
    public static int SCREEN_SIZE_Y = 768;

//    public static final int SCREEN_SIZE_X = 1920; //1366;
//    public static final int SCREEN_SIZE_Y = 1080; //768;

    public static final int VIEWPORT_WIDTH = 30;
    public static final int VIEWPORT_HEIGHT = 17;

    public static final int SCREEN_HUD_RATIO = 4;

    public static final int HUD_VIEWPORT_WIDTH = SCREEN_SIZE_X / SCREEN_HUD_RATIO;
    public static final int HUD_VIEWPORT_HEIGHT = SCREEN_SIZE_Y / SCREEN_HUD_RATIO;

    public static int treeSpread = 5;
}
