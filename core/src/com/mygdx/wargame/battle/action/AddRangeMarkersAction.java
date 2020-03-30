package com.mygdx.wargame.battle.action;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.battle.rules.facade.WeaponRangeMarkerUpdater;
import com.mygdx.wargame.common.mech.AbstractMech;
import com.mygdx.wargame.common.pilot.Pilot;

public class AddRangeMarkersAction extends Action {

    private BattleMap battleMap;
    private WeaponRangeMarkerUpdater weaponRangeMarkerUpdater = new WeaponRangeMarkerUpdater();
    private AbstractMech attackingMech;
    private Pilot attackingPilot;

    public AddRangeMarkersAction(BattleMap battleMap, AbstractMech attackingMech, Pilot attackingPilot) {
        this.battleMap = battleMap;
        this.attackingMech = attackingMech;
        this.attackingPilot = attackingPilot;
    }


    @Override
    public boolean act(float delta) {
        weaponRangeMarkerUpdater.updateWeaponRangeMarkers(battleMap, attackingMech, attackingPilot);
        return true;
    }
}
