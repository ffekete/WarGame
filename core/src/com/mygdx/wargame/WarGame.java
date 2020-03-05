package com.mygdx.wargame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.wargame.battle.lock.ActionLock;
import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.battle.map.BattleMapConfig;
import com.mygdx.wargame.battle.map.TerrainType;
import com.mygdx.wargame.battle.screen.BattleScreen;
import com.mygdx.wargame.battle.screen.BattleScreenInputDataStubber;
import com.mygdx.wargame.battle.screen.ScreenLoader;
import com.mygdx.wargame.common.ScreenRegister;

public class WarGame extends Game {

    private final ScreenRegister screenRegister = ScreenRegister.I;

    @Override
    public void create() {
        screenRegister.setGame(this);
        BattleScreen battleScreen = screenRegister.getBattleScreen();

        AssetManager assetManager = new AssetManager();
        ScreenLoader screenLoader = new ScreenLoader(assetManager);

        battleScreen.load(screenLoader);
        this.setScreen(battleScreen);
    }

}
