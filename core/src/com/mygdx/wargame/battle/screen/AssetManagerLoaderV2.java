package com.mygdx.wargame.battle.screen;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public class AssetManagerLoaderV2 {

    public static AssetManager assetManager;

    public AssetManagerLoaderV2(AssetManager aAssetManager) {
        assetManager = aAssetManager;
    }

    public void load() {

        assetManager.load("tiles/GrassBig.png", Texture.class);
        assetManager.load("tiles/GrassBigDamaged.png", Texture.class);
        assetManager.load("tiles/GrassBigBuilding.png", Texture.class);

        assetManager.load("tiles/GrassBigForest.png", Texture.class);
        assetManager.load("tiles/GrassBigLow.png", Texture.class);
        assetManager.load("tiles/GrassBigForestDamaged.png", Texture.class);
        assetManager.load("tiles/GrassBigMountain.png", Texture.class);
        assetManager.load("tiles/GrassBigHills.png", Texture.class);

        assetManager.load("mechs/Templar.png", Texture.class);
        assetManager.load("mechs/Gunner.png", Texture.class);
        assetManager.load("mechs/MobileArtillery.png", Texture.class);
        assetManager.load("mechs/Artillery.png", Texture.class);
        assetManager.load("mechs/Tank.png", Texture.class);
        assetManager.load("mechs/Fighter.png", Texture.class);
        assetManager.load("mechs/Shadow.png", Texture.class);
        assetManager.load("mechs/MechIdle.png", Texture.class);
        assetManager.load("mechs/MechWalk.png", Texture.class);
        assetManager.load("mechs/FighterUp.png", Texture.class);
        assetManager.load("mechs/FighterRight.png", Texture.class);


        assetManager.load("info/PathMarker.png", Texture.class);
        assetManager.load("info/RangeMarker.png", Texture.class);
        assetManager.load("info/MovementMarker.png", Texture.class);
        assetManager.load("info/MovementDirectionUp.png", Texture.class);
        assetManager.load("info/MovementDirectionDown.png", Texture.class);
        assetManager.load("info/MovementDirectionLeft.png", Texture.class);
        assetManager.load("info/MovementDirectionRight.png", Texture.class);
        assetManager.load("info/SelectionMarker.png", Texture.class);
        assetManager.load("info/EnemyMarker.png", Texture.class);

        assetManager.load("hud/TileInfoPanel.png", Texture.class);
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

        assetManager.load("hud/SmallButtonUp.png", Texture.class);
        assetManager.load("hud/SmallButtonDown.png", Texture.class);
        assetManager.load("hud/SmallButtonOver.png", Texture.class);
        assetManager.load("hud/PilotNameLabel.png", Texture.class);

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
        assetManager.load("mainmenu/BackGround.jpg", Texture.class);
        assetManager.load("mainmenu/MainMenuButtonBackground.png", Texture.class);

        assetManager.load("bullets/Laser.png", Texture.class);
        assetManager.load("bullets/PlasmaBullet.png", Texture.class);
        assetManager.load("bullets/LaserHit.png", Texture.class);
        assetManager.load("bullets/PlasmaHit.png", Texture.class);
        assetManager.load("bullets/Missile.png", Texture.class);
        assetManager.load("bullets/CannonBullet.png", Texture.class);
        assetManager.load("bullets/CannonHit.png", Texture.class);
        assetManager.load("bullets/IonHit.png", Texture.class);
        assetManager.load("bullets/IonBullet.png", Texture.class);
        assetManager.load("bullets/FlamerBullet.png", Texture.class);
        assetManager.load("bullets/FlamerHit.png", Texture.class);
        assetManager.load("bullets/Smoke.png", Texture.class);

        assetManager.load("effects/Explosion.png", Texture.class);

        assetManager.load("windows/Window.png", Texture.class);
        assetManager.load("windows/Tooltip.png", Texture.class);

        assetManager.load("common/CheckboxChecked.png", Texture.class);
        assetManager.load("common/CheckboxOnChecked.png", Texture.class);
        assetManager.load("common/CheckboxUnchecked.png", Texture.class);
        assetManager.load("common/CheckboxOnUnchecked.png", Texture.class);

        assetManager.load("portraits/Portrait01.png", Texture.class);
        assetManager.load("portraits/Portrait02.png", Texture.class);
        assetManager.load("portraits/Portrait03.png", Texture.class);
        assetManager.load("portraits/Portrait04.png", Texture.class);
        assetManager.load("portraits/Portrait05.png", Texture.class);

        assetManager.finishLoading();
    }
}
