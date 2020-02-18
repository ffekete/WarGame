package com.mygdx.wargame.battle.screen;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public class ScreenLoader {

    private AssetManager assetManager;

    public ScreenLoader() {
        this.assetManager = new AssetManager();
    }

    public void load() {
        assetManager = new AssetManager();
        assetManager.load("Marauder.png", Texture.class);
        assetManager.load("Marauder.png", Texture.class);
        assetManager.load("WreckingBall.png", Texture.class);
        assetManager.load("tileset/Dirt.png", Texture.class);
        assetManager.load("SelectionMarker.png", Texture.class);
        assetManager.load("DesertTile.png", Texture.class);
        assetManager.load("Grassland.png", Texture.class);
        assetManager.load("objects/Crater.png", Texture.class);
        assetManager.load("variation/Trees.png", Texture.class);
        assetManager.load("variation/Trees03.png", Texture.class);
        assetManager.load("variation/Trees02.png", Texture.class);
        assetManager.load("variation/Trees04.png", Texture.class);
        assetManager.load("PlasmaBullet.png", Texture.class);
        assetManager.load("CannonBullet.png", Texture.class);
        assetManager.load("Missile.png", Texture.class);
        assetManager.load("Explosion.png", Texture.class);
        assetManager.load("Laser.png", Texture.class);
        assetManager.load("Ion.png", Texture.class);
        assetManager.load("MachineGun.png", Texture.class);
        assetManager.load("Shield.png", Texture.class);
        assetManager.load("MovementMarker.png", Texture.class);
        assetManager.load("skin/EndTurnButtonUp.png", Texture.class);
        assetManager.load("skin/EndTurnButtonDown.png", Texture.class);
        assetManager.finishLoading();
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }
}
