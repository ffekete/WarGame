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

        assetManager.load("HeatProgressBarBg.png", Texture.class);
        assetManager.load("StabilityProgressBarBg.png", Texture.class);
        assetManager.load("HeatProgressBarKnob.png", Texture.class);
        assetManager.load("StabilityProgressBarKnob.png", Texture.class);
        assetManager.load("WayPoint.png", Texture.class);
        assetManager.load("WayPointEnd.png", Texture.class);
        assetManager.load("HealthOverlay.png", Texture.class);
        assetManager.load("Marauder.png", Texture.class);
        assetManager.load("Giant.png", Texture.class);
        assetManager.load("Colossus.png", Texture.class);
        assetManager.load("PowerArmor.png", Texture.class);
        assetManager.load("Scout.png", Texture.class);
        assetManager.load("WreckingBall.png", Texture.class);

        assetManager.load("tileset/Dirt.png", Texture.class);
        assetManager.load("tileset/Water.png", Texture.class);

        assetManager.load("SelectionMarker.png", Texture.class);
        assetManager.load("DesertTile.png", Texture.class);
        assetManager.load("Grass.png", Texture.class);
        assetManager.load("GrassDecoration.png", Texture.class);
        assetManager.load("GrassDecoration02.png", Texture.class);
        assetManager.load("GrassDecoration03.png", Texture.class);
        assetManager.load("Flame.png", Texture.class);
        assetManager.load("objects/Crater.png", Texture.class);
        assetManager.load("Splash.png", Texture.class);

        assetManager.load("details/HeadIcon.png", Texture.class);
        assetManager.load("details/TorsoIcon.png", Texture.class);
        assetManager.load("details/LeftArmIcon.png", Texture.class);
        assetManager.load("details/RightArmIcon.png", Texture.class);
        assetManager.load("details/LeftLegIcon.png", Texture.class);
        assetManager.load("details/RightLegIcon.png", Texture.class);
        assetManager.load("details/ShieldIcon.png", Texture.class);
        assetManager.load("details/ShieldComponentIcon.png", Texture.class);
        assetManager.load("details/ButtonBg.png", Texture.class);
        assetManager.load("details/ButtonBgDown.png", Texture.class);
        assetManager.load("details/ButtonBgOver.png", Texture.class);

        assetManager.load("targeting/TargetHead.png", Texture.class);
        assetManager.load("targeting/TargetTorso.png", Texture.class);
        assetManager.load("targeting/TargetLeftArm.png", Texture.class);
        assetManager.load("targeting/TargetRightArm.png", Texture.class);
        assetManager.load("targeting/TargetLeftLeg.png", Texture.class);
        assetManager.load("targeting/TargetRightLeg.png", Texture.class);

        assetManager.load("health/HeadHealthIcon.png", Texture.class);
        assetManager.load("health/TorsoHealthIcon.png", Texture.class);
        assetManager.load("health/LeftArmHealthIcon.png", Texture.class);
        assetManager.load("health/RightArmHealthIcon.png", Texture.class);
        assetManager.load("health/LeftLegHealthIcon.png", Texture.class);
        assetManager.load("health/RightLegHealthIcon.png", Texture.class);

        assetManager.load("Birds.png", Texture.class);
        assetManager.load("Shadow.png", Texture.class);

        assetManager.load("hud/AmmoIcon.png", Texture.class);
        assetManager.load("hud/HealthIcon.png", Texture.class);
        assetManager.load("hud/HeatIcon.png", Texture.class);
        assetManager.load("hud/StabilityIcon.png", Texture.class);

        assetManager.load("variation/Tree01.png", Texture.class);
        assetManager.load("variation/Tree02.png", Texture.class);
        assetManager.load("variation/Tree03.png", Texture.class);
        assetManager.load("variation/Tree04.png", Texture.class);

        assetManager.load("PlasmaBullet.png", Texture.class);
        assetManager.load("CannonBullet.png", Texture.class);
        assetManager.load("Missile.png", Texture.class);
        assetManager.load("Explosion.png", Texture.class);
        assetManager.load("MissileExplosion.png", Texture.class);
        assetManager.load("Laser.png", Texture.class);
        assetManager.load("Ion.png", Texture.class);
        assetManager.load("MachineGun.png", Texture.class);
        assetManager.load("Shielded.png", Texture.class);
        assetManager.load("FriendlyMarker.png", Texture.class);
        assetManager.load("EnemyMarker.png", Texture.class);
        assetManager.load("MovementMarker.png", Texture.class);

        assetManager.load("skin/InfoPanel.png", Texture.class);
        assetManager.load("skin/EndTurnButtonUp.png", Texture.class);
        assetManager.load("skin/EndTurnButtonDown.png", Texture.class);
        assetManager.load("skin/BigInfoPanel.png", Texture.class);
        assetManager.load("skin/CalledShotUp.png", Texture.class);
        assetManager.load("skin/CalledShotDown.png", Texture.class);
        assetManager.load("skin/HideButtonUp.png", Texture.class);
        assetManager.load("skin/HideButtonDown.png", Texture.class);
        assetManager.load("skin/SimplePanel.png", Texture.class);

        assetManager.load("localmenu/AttackButton.png", Texture.class);
        assetManager.load("localmenu/CalledShotUp.png", Texture.class);
        assetManager.load("localmenu/DetailsButton.png", Texture.class);
        assetManager.load("localmenu/ExitLocalMenuButton.png", Texture.class);
        assetManager.load("localmenu/PilotButton.png", Texture.class);
        assetManager.finishLoading();
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }
}
