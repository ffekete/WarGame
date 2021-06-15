package com.mygdx.wargame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.google.common.collect.ImmutableMap;
import com.mygdx.wargame.battle.screen.AssetManagerLoaderV2;
import com.mygdx.wargame.battle.screen.BattleScreenInputData;
import com.mygdx.wargame.battle.screen.BattleScreenV2;
import com.mygdx.wargame.common.ScreenRegister;
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

        AbstractMech mech = new Gunner("PlayerMech");
        mech.setActive(true);
        mech.setPosition(-1, -1);

        AbstractMech mobileArtillery = new MobileArtillery("PlayerMobileArtillery");
        mobileArtillery.setActive(true);
        mobileArtillery.setPosition(-1, -1);


        AbstractMech fighter = new Fighter("PlayerFighter");
        fighter.setActive(true);
        fighter.setPosition(-1, -1);


        AbstractMech tank = new Tank("AITank");
        tank.setActive(true);
        tank.setPosition(-1, -1);

        AbstractMech artillery = new Artillery("AIArtillery");
        artillery.setActive(true);
        artillery.setPosition(-1, -1);

        AbstractMech mech2 = new Templar("AITemplar");
        mech2.setActive(true);
        mech2.setPosition(-1, -1);


        battleScreenInputData.setPlayerTeam(ImmutableMap.<AbstractMech, Pilot>builder()
                .put(mech, new PilotCreator(assetManager).getPilot())
                .put(mobileArtillery, new PilotCreator(assetManager).getPilot())
                .put(fighter, new PilotCreator(assetManager).getPilot())
                .build());
        battleScreenInputData.setAiTeam(ImmutableMap.<AbstractMech, Pilot>builder()
                .put(mech2, new PilotCreator(assetManager).getPilot())
                .put(artillery, new PilotCreator(assetManager).getPilot())
                .put(tank, new PilotCreator(assetManager).getPilot())
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
