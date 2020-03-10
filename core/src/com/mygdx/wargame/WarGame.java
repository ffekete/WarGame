package com.mygdx.wargame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.mygdx.wargame.battle.screen.BattleScreen;
import com.mygdx.wargame.battle.screen.AssetManagerLoader;
import com.mygdx.wargame.battle.screenv2.AssetManagerLoaderV2;
import com.mygdx.wargame.battle.screenv2.ScreenV2;
import com.mygdx.wargame.common.ScreenRegister;
import com.mygdx.wargame.config.Config;
import com.mygdx.wargame.mainmenu.MainMenuScreen;
import com.mygdx.wargame.options.OptionsScreen;
import com.mygdx.wargame.summary.SummaryScreen;

public class WarGame extends Game {

    private final ScreenRegister screenRegister = ScreenRegister.I;

    private AssetManager assetManager;
    private AssetManagerLoaderV2 assetManagerLoaderV2;
    private AssetManagerLoader assetManagerLoader;

    @Override
    public void create() {
        Config.load();
        screenRegister.setGame(this);
        assetManager = new AssetManager();
        assetManagerLoaderV2 = new AssetManagerLoaderV2(assetManager);
        assetManagerLoader = new AssetManagerLoader(assetManager);

        assetManagerLoaderV2.load();
        assetManagerLoader.load();

        ScreenRegister.I.getMainMenuScreen().load(assetManager);

        this.setScreen(ScreenRegister.I.getMainMenuScreen());
    }

    public void showBattleScreen() {
        ScreenV2 battleScreen = screenRegister.getBattleScreenV2();
        battleScreen.load(assetManagerLoaderV2);
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
