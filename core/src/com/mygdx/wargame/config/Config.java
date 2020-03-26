package com.mygdx.wargame.config;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.Json;

import java.util.function.Supplier;

public class Config {
    public static class Cfg {
        public int screenSizeX = SCREEN_SIZE_X;
        public int screenSizeY = SCREEN_SIZE_Y;

        public int cloudDensity = 500;
        public int treeSpread = 5;

        public int getScreenSizeX() {
            return screenSizeX;
        }

        public int getScreenSizeY() {
            return screenSizeY;
        }

        public int getCloudDensity() {
            return cloudDensity;
        }

        public int getTreeSpread() {
            return treeSpread;
        }

        public boolean showMovementMarkers = true;

        public boolean isShowMovementMarkers() {
            return showMovementMarkers;
        }
    }

    public static Cfg cfg = new Cfg();

    public static int SCREEN_SIZE_X = 1920;
    public static int SCREEN_SIZE_Y = 1080;

    public static final int SCREEN_HUD_RATIO = 4;

    public static Supplier<Integer> HUD_VIEWPORT_WIDTH = () -> (int)(1920 * 0.75f);
    public static Supplier<Integer> HUD_VIEWPORT_HEIGHT = () -> (int)(1080 * 0.75f);;

    public static int treeSpread = 5;

    public static boolean showDirectionMarkers = false;

    public static int CLOUD_DENSITY = 500;

    public static boolean showMovementMarkers = true;

    public static void load() {
        try {
            FileHandle file = Gdx.files.getFileHandle("configuration.json", Files.FileType.Local);
            Json json = new Json();
            json.setUsePrototypes(false);
            String res = file.readString();
            cfg = json.fromJson(Cfg.class, res);

            SCREEN_SIZE_X = cfg.screenSizeX;
            SCREEN_SIZE_Y = cfg.screenSizeY;
            CLOUD_DENSITY = cfg.cloudDensity;
            treeSpread = cfg.treeSpread;
            showMovementMarkers = cfg.showMovementMarkers;

        } catch (GdxRuntimeException gre) {
            cfg = new Cfg();
            save();
        }
    }

    public static void save() {
        FileHandle file = Gdx.files.getFileHandle("configuration.json", Files.FileType.Local);
        Json json = new Json();
        json.setUsePrototypes(false);
        file.writeBytes(json.toJson(cfg).getBytes(), false);
    }
}
