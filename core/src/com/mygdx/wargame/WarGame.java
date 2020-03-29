package com.mygdx.wargame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.google.common.collect.ImmutableMap;
import com.mygdx.wargame.battle.screen.AssetManagerLoaderV2;
import com.mygdx.wargame.battle.screen.BattleScreenInputData;
import com.mygdx.wargame.battle.screen.BattleScreenV2;
import com.mygdx.wargame.common.ScreenRegister;
import com.mygdx.wargame.common.component.weapon.ballistic.LargeCannon;
import com.mygdx.wargame.common.component.weapon.ballistic.SmallCannon;
import com.mygdx.wargame.common.component.weapon.flamer.Flamer;
import com.mygdx.wargame.common.component.weapon.ion.EnhancedSmallIonCannon;
import com.mygdx.wargame.common.component.weapon.laser.ExtendedRangeLargeLaser;
import com.mygdx.wargame.common.component.weapon.laser.LargeLaser;
import com.mygdx.wargame.common.component.weapon.laser.SmallLaser;
import com.mygdx.wargame.common.component.weapon.missile.MissileLauncher;
import com.mygdx.wargame.common.component.weapon.plasma.PlasmaCannon;
import com.mygdx.wargame.common.mech.AbstractMech;
import com.mygdx.wargame.common.mech.BodyPart;
import com.mygdx.wargame.common.mech.Gunner;
import com.mygdx.wargame.common.mech.Mech;
import com.mygdx.wargame.common.mech.Templar;
import com.mygdx.wargame.common.pilot.Pilot;
import com.mygdx.wargame.common.pilot.PilotCreator;
import com.mygdx.wargame.config.Config;
import com.mygdx.wargame.mainmenu.MainMenuScreen;
import com.mygdx.wargame.options.OptionsScreen;
import com.mygdx.wargame.summary.SummaryScreen;

public class WarGame extends Game {

    private final ScreenRegister screenRegister = ScreenRegister.I;

    private AssetManager assetManager;
    private AssetManagerLoaderV2 assetManagerLoaderV2;

    @Override
    public void create() {
        Config.load();
        screenRegister.setGame(this);
        assetManager = new AssetManager();
        assetManagerLoaderV2 = new AssetManagerLoaderV2(assetManager);

        assetManagerLoaderV2.load();

        ScreenRegister.I.getMainMenuScreen().load(assetManager);

        this.setScreen(ScreenRegister.I.getMainMenuScreen());
    }

    public void showBattleScreen() {
        BattleScreenV2 battleScreen = screenRegister.getBattleScreenV2();

        BattleScreenInputData battleScreenInputData = new BattleScreenInputData();

        AbstractMech mech = new Gunner("Player",  assetManagerLoaderV2);
        mech.setActive(true);
        mech.setPosition(0,0);
        mech.setStability(100);
        mech.addWeapon(BodyPart.LeftArm, new SmallCannon());
        mech.addWeapon(BodyPart.LeftArm, new SmallCannon());

        mech.addWeapon(BodyPart.RightArm, new SmallCannon());
        mech.addWeapon(BodyPart.RightArm, new SmallCannon());

        AbstractMech mech2 = new Templar("AI", assetManagerLoaderV2);
        mech2.setActive(true);
        mech2.setPosition(5, 5);
        mech2.setStability(100);
        mech2.addWeapon(BodyPart.RightArm, new LargeLaser());
        mech2.addWeapon(BodyPart.LeftArm, new LargeLaser());


        battleScreenInputData.setPlayerTeam(ImmutableMap.<AbstractMech, Pilot>builder().put(mech, new PilotCreator().getPilot()).build());
        battleScreenInputData.setAiTeam(ImmutableMap.<AbstractMech, Pilot>builder().put(mech2, new PilotCreator().getPilot()).build());


        battleScreen.load(assetManagerLoaderV2, battleScreenInputData);
        this.setScreen(battleScreen);
    }

    public void showOptionsScreen() {
        OptionsScreen optionsScreen = screenRegister.getOptionsScreen();
        optionsScreen.load(assetManager);
        this.setScreen(optionsScreen);
    }

    public void showMainMenuScreen() {
        MainMenuScreen mainMenuScreen = screenRegister.getMainMenuScreen();
        mainMenuScreen.load(assetManager);
        this.setScreen(mainMenuScreen);
    }

    public void showSummaryScreen() {
        SummaryScreen summaryScreen = screenRegister.getSummaryScreen();
        summaryScreen.load(assetManager);
        this.setScreen(summaryScreen);
    }
}
