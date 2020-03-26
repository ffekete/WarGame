package com.mygdx.wargame.battle.unit.action;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.battle.unit.Direction;
import com.mygdx.wargame.common.mech.Mech;

public class AddDirectionMarkerAction extends Action {

    private Float tx = null, ty = null;
    private Mech target;
    private BattleMap battleMap;

    public AddDirectionMarkerAction(float tx, float ty, Mech target, BattleMap battleMap) {
        this.tx = tx;
        this.ty = ty;
        this.target = target;
        this.battleMap = battleMap;
    }

    public AddDirectionMarkerAction(Mech target, BattleMap battleMap) {
        this.target = target;
        this.battleMap = battleMap;
    }

    @Override
    public boolean act(float delta) {
        if(tx == null && ty == null) {
            battleMap.addDirectionMarker(target.getDirection(), (int)target.getX(), (int)target.getY());
        }else {
            battleMap.addDirectionMarker(target.getDirection(), (int)tx.floatValue(), (int)ty.floatValue());
        }

        return true;
    }
}
