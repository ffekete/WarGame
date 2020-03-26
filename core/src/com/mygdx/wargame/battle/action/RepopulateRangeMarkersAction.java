package com.mygdx.wargame.battle.action;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.battle.rules.facade.WeaponRangeMarkerUpdater;
import com.mygdx.wargame.common.mech.AbstractMech;
import com.mygdx.wargame.common.pilot.Pilot;

public class RepopulateRangeMarkersAction extends Action {

    private BattleMap battleMap;
    private Pilot pilot;
    private AbstractMech mech;
    private WeaponRangeMarkerUpdater weaponRangeMarkerUpdater;

    public RepopulateRangeMarkersAction(BattleMap battleMap, Pilot pilot, AbstractMech mech, WeaponRangeMarkerUpdater weaponRangeMarkerUpdater) {
        this.battleMap = battleMap;
        this.pilot = pilot;
        this.mech = mech;
        this.weaponRangeMarkerUpdater = weaponRangeMarkerUpdater;
    }

    @Override
    public boolean act(float delta) {
        weaponRangeMarkerUpdater.updateWeaponRangeMarkers(battleMap, mech, pilot);
        return true;
    }
}
