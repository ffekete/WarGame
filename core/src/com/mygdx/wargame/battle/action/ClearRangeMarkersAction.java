package com.mygdx.wargame.battle.action;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.mygdx.wargame.battle.map.BattleMap;

public class ClearRangeMarkersAction extends Action {

    private BattleMap battleMap;

    public ClearRangeMarkersAction(BattleMap battleMap) {
        this.battleMap = battleMap;
    }


    @Override
    public boolean act(float delta) {
        battleMap.clearRangeMarkers();
        return true;
    }
}
