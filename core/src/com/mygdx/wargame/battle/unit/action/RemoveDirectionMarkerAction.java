package com.mygdx.wargame.battle.unit.action;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.common.mech.Mech;

public class RemoveDirectionMarkerAction extends Action {

    private float tx, ty;
    private BattleMap battleMap;

    public RemoveDirectionMarkerAction(float tx, float ty, BattleMap battleMap) {
        this.tx = tx;
        this.ty = ty;

        this.battleMap = battleMap;
    }

    @Override
    public boolean act(float delta) {
        battleMap.removeDirectionMarker((int)tx, (int)ty);

        return true;
    }
}
