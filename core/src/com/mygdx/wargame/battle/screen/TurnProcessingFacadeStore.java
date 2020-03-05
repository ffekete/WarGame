package com.mygdx.wargame.battle.screen;

import com.mygdx.wargame.battle.rules.facade.TurnProcessingFacade;

public class TurnProcessingFacadeStore {

    private TurnProcessingFacade turnProcessingFacade;

    public TurnProcessingFacade getTurnProcessingFacade() {
        return turnProcessingFacade;
    }

    public void setTurnProcessingFacade(TurnProcessingFacade turnProcessingFacade) {
        this.turnProcessingFacade = turnProcessingFacade;
    }
}
