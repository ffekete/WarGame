package com.mygdx.wargame;

import com.badlogic.gdx.Game;
import com.mygdx.wargame.battle.screen.BattleScreen;
import com.mygdx.wargame.common.ScreenRegister;

public class WarGame extends Game {

    private final ScreenRegister screenRegister = ScreenRegister.I;

    @Override
    public void create() {
        screenRegister.setGame(this);
        this.setScreen(ScreenRegister.I.getBattleScreen());
    }

}
