package com.mygdx.wargame;

import com.badlogic.gdx.Game;
import com.mygdx.wargame.battle.screen.BattleScreen;

public class WarGame extends Game {

    private BattleScreen battleScreen;

    @Override
    public void create() {

        battleScreen = new BattleScreen();
        this.setScreen(battleScreen);
    }

}
