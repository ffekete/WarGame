package com.mygdx.wargame;

import com.badlogic.gdx.Game;
import com.mygdx.wargame.battle.controller.SelectionController;
import com.mygdx.wargame.battle.screen.BattleScreen;

import java.util.HashSet;

public class WarGame extends Game {

	private BattleScreen battleScreen;

	@Override
	public void create () {
		SelectionController selectionController =  new SelectionController(null);
		battleScreen = new BattleScreen(selectionController);
		this.setScreen(battleScreen);
	}

}
