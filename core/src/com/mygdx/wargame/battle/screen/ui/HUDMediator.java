package com.mygdx.wargame.battle.screen.ui;

public class HUDMediator {

    private HudElementsFacade hudElementsFacade;
    private BattleGameMenuFacade battleGameMenuFacade;
    private GameEndFacade gameEndFacade;

    public HUDMediator() {
    }

    public void hideAll() {
        hudElementsFacade.hide();
    }

    public void setHudElementsFacade(HudElementsFacade hudElementsFacade) {
        this.hudElementsFacade = hudElementsFacade;
    }


    public HudElementsFacade getHudElementsFacade() {
        return hudElementsFacade;
    }

    public BattleGameMenuFacade getBattleGameMenuFacade() {
        return battleGameMenuFacade;
    }

    public void setBattleGameMenuFacade(BattleGameMenuFacade battleGameMenuFacade) {
        this.battleGameMenuFacade = battleGameMenuFacade;
    }

    public GameEndFacade getGameEndFacade() {
        return gameEndFacade;
    }

    public void setGameEndFacade(GameEndFacade gameEndFacade) {
        this.gameEndFacade = gameEndFacade;
    }
}
