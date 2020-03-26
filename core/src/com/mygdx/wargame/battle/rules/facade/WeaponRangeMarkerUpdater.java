package com.mygdx.wargame.battle.rules.facade;

import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.battle.rules.calculator.RangeCalculator;
import com.mygdx.wargame.common.mech.AbstractMech;
import com.mygdx.wargame.common.pilot.Pilot;
import com.mygdx.wargame.util.MathUtils;

public class WeaponRangeMarkerUpdater {

    private RangeCalculator rangeCalculator = new RangeCalculator();

    public void updateWeaponRangeMarkers(BattleMap battleMap, AbstractMech selectedMech, Pilot selectedPilot) {

        battleMap.clearRangeMarkers();

        int minRange = rangeCalculator.calculateAllWeaponsRange(selectedPilot, selectedMech);
        for(int i = 0; i < BattleMap.WIDTH; i++) {
            for (int j = 0; j < BattleMap.HEIGHT; j++) {
                if(MathUtils.getDistance(i,j, selectedMech.getX(), selectedMech.getY()) <= minRange) {
                    battleMap.addRangeMarker(i,j);
                }
            }
        }
    }

}
