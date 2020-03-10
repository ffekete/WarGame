package com.mygdx.wargame.battle.screen;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public class AssetManagerLoaderV2 {

    private AssetManager assetManager;

    public AssetManagerLoaderV2(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    public void load() {

        assetManager.load("tiles/Grass.png", Texture.class);
        assetManager.load("IsometricScout.png", Texture.class);
        assetManager.finishLoading();
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }
}
