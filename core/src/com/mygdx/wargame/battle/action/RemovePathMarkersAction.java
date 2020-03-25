package com.mygdx.wargame.battle.action;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.mygdx.wargame.battle.map.BattleMap;

public class RemovePathMarkersAction extends Action {

    private BattleMap battleMap;

    public RemovePathMarkersAction(BattleMap battleMap) {
        this.battleMap = battleMap;
    }

    @Override
    public boolean act(float delta) {
        battleMap.clearPathMarkers();
        return true;
    }
}
