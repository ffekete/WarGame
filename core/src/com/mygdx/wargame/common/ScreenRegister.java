package com.mygdx.wargame.common;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.mygdx.wargame.WarGame;
import com.mygdx.wargame.battle.screen.BattleScreen;
import com.mygdx.wargame.mainmenu.MainMenuScreen;
import com.mygdx.wargame.options.OptionsScreen;
import com.mygdx.wargame.summary.SummaryScreen;

public class ScreenRegister {

    public static final ScreenRegister I = new ScreenRegister();

    private WarGame game;

    private BattleScreen battleScreen = new BattleScreen();
    private SummaryScreen summaryScreen = new SummaryScreen();
    private OptionsScreen optionsScreen = new OptionsScreen();
    private MainMenuScreen mainMenuScreen = new MainMenuScreen();
    private Screen lastScreen;

    public BattleScreen getBattleScreen() {
        return battleScreen;
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
