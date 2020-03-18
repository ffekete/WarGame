package com.mygdx.wargame.common;

import com.badlogic.gdx.Screen;
import com.mygdx.wargame.WarGame;
import com.mygdx.wargame.battle.screen.BattleScreenV2;
import com.mygdx.wargame.mainmenu.MainMenuScreen;
import com.mygdx.wargame.options.OptionsScreen;
import com.mygdx.wargame.summary.SummaryScreen;

public class ScreenRegister {

    public static final ScreenRegister I = new ScreenRegister();

    private WarGame game;

    private BattleScreenV2 battleScreenV2 = new BattleScreenV2();
    private SummaryScreen summaryScreen = new SummaryScreen();
    private OptionsScreen optionsScreen = new OptionsScreen();
    private MainMenuScreen mainMenuScreen = new MainMenuScreen();
    private Screen lastScreen;

    public BattleScreenV2 getBattleScreenV2() {
        return battleScreenV2;
    }

    public SummaryScreen getSummaryScreen() {
        return summaryScreen;
    }

    public WarGame getGame() {
        return game;
    }

    public void setGame(WarGame game) {
        this.game = game;
    }

    public OptionsScreen getOptionsScreen() {
        return optionsScreen;
    }

    public void setLastScreen(Screen lastScreen) {
        this.lastScreen = lastScreen;
    }

    public Screen getLastScreen() {
        return lastScreen;
    }

    public MainMenuScreen getMainMenuScreen() {
        return mainMenuScreen;
    }
}
