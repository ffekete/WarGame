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
import com.mygdx.wargame.common.mech.*;
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

        AbstractMech mobileArtillery = new MobileArtillery("Player", assetManagerLoaderV2);
        mobileArtillery.setActive(true);
        mobileArtillery.setPosition(3, 0);


        AbstractMech fighter = new Fighter("Player", assetManagerLoaderV2);
        fighter.setActive(true);
        fighter.setPosition(5, 0);


        AbstractMech tank = new Tank("AI", assetManagerLoaderV2);
        tank.setActive(true);
        tank.setPosition(5, 7);

        AbstractMech artillery = new Artillery("AI", assetManagerLoaderV2);
        artillery.setActive(true);
        artillery.setPosition(5, 6);

        AbstractMech mech2 = new Templar("AI", assetManagerLoaderV2);
        mech2.setActive(true);
        mech2.setPosition(5, 5);


        battleScreenInputData.setPlayerTeam(ImmutableMap.<AbstractMech, Pilot>builder()
                .put(mech, new PilotCreator().getPilot())
                .put(mobileArtillery, new PilotCreator().getPilot())
                .put(fighter, new PilotCreator().getPilot())
                .build());
        battleScreenInputData.setAiTeam(ImmutableMap.<AbstractMech, Pilot>builder()
                .put(mech2, new PilotCreator().getPilot())
                .put(artillery, new PilotCreator().getPilot())
                .put(tank, new PilotCreator().getPilot())
                .build());


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
