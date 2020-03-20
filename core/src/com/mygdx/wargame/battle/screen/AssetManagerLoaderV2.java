package com.mygdx.wargame.battle.screen;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public class AssetManagerLoaderV2 {

    private AssetManager assetManager;

    public AssetManagerLoaderV2(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    public void load() {

        assetManager.load("tiles/GrassBig.png", Texture.class);
        assetManager.load("tiles/GrassBigDamaged.png", Texture.class);
        assetManager.load("tiles/GrassBigCity.png", Texture.class);
        assetManager.load("tiles/GrassBigCityDamaged.png", Texture.class);
        assetManager.load("tiles/GrassBigForest.png", Texture.class);
        assetManager.load("tiles/GrassBigForestDamaged.png", Texture.class);
        assetManager.load("tiles/GrassBigForestLake.png", Texture.class);
        assetManager.load("tiles/GrassBigMountain.png", Texture.class);
        assetManager.load("tiles/GrassBigPowerPlant.png", Texture.class);
        assetManager.load("tiles/GrassBigPowerPlantDestroyed.png", Texture.class);

        assetManager.load("mechs/Templar.png", Texture.class);
        assetManager.load("mechs/Gunner.png", Texture.class);

        assetManager.load("info/PathMarker.png", Texture.class);

        assetManager.load("hud/ArmorIcon.png", Texture.class);
        assetManager.load("hud/ShieldIcon.png", Texture.class);
        assetManager.load("hud/AmmoIcon.png", Texture.class);
        assetManager.load("hud/HealthIcon.png", Texture.class);
        assetManager.load("hud/HeatIcon.png", Texture.class);
        assetManager.load("hud/StabilityIcon.png", Texture.class);

        assetManager.load("hud/EndTurnButtonUp.png", Texture.class);
        assetManager.load("hud/EndTurnButtonDown.png", Texture.class);

        assetManager.load("hud/MainMenuSmallButtonUp.png", Texture.class);
        assetManager.load("hud/MainMenuSmallButtonDown.png", Texture.class);

        assetManager.load("hud/ShowMovementMarkersSmallButtonUp.png", Texture.class);
        assetManager.load("hud/ShowMovementMarkersSmallButtonDown.png", Texture.class);
        assetManager.load("hud/HideMovementMarkersSmallButtonUp.png", Texture.class);
        assetManager.load("hud/HideMovementMarkersSmallButtonDown.png", Texture.class);
        assetManager.load("hud/ShowMovementDirectionsSmallButtonUp.png", Texture.class);
        assetManager.load("hud/ShowMovementDirectionsSmallButtonDown.png", Texture.class);
        assetManager.load("hud/HideMovementDirectionsSmallButtonUp.png", Texture.class);
        assetManager.load("hud/HideMovementDirectionsSmallButtonDown.png", Texture.class);

        assetManager.load("mainmenu/MainMenuButtonUp.png", Texture.class);
        assetManager.load("mainmenu/MainMenuButtonDown.png", Texture.class);
        assetManager.load("mainmenu/MainMenuButtonOver.png", Texture.class);

        assetManager.load("bullets/Laser.png", Texture.class);
        assetManager.load("bullets/Missile.png", Texture.class);

        assetManager.load("effects/Explosion.png", Texture.class);

        assetManager.finishLoading();
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }
}
