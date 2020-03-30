package com.mygdx.wargame.battle.action;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.battle.map.decoration.SelectionMarker;

public class ClearMovementMarkersAction extends Action {

    private BattleMap battleMap;

    public ClearMovementMarkersAction(BattleMap battleMap) {
        this.battleMap = battleMap;
    }


    @Override
    public boolean act(float delta) {
        battleMap.clearMovementMarkers();
        return true;
    }
}
