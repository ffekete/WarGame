package com.mygdx.wargame.battle.rules.calculator;

import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.common.component.heatsink.HeatSink;
import com.mygdx.wargame.common.component.weapon.Status;
import com.mygdx.wargame.common.mech.Mech;
import com.mygdx.wargame.common.pilot.Perks;
import com.mygdx.wargame.common.pilot.Pilot;

public class HeatCalculator {

    public int calculateCooling(Pilot targetPilot, Mech targetMech, BattleMap battleMap) {

        int baseValue = 20;

        int heatDissipation = targetMech.getAllComponents().stream()
                .filter(c -> c.getStatus() != Status.Destroyed)
                .filter(c -> HeatSink.class.isAssignableFrom(c.getClass()))
                .map(c -> ((HeatSink) c).getHeatDissipation())
                .reduce((a, b) -> a + b).orElse(0);

        baseValue += heatDissipation;

        int groundModifier = battleMap.getTile(targetMech.getX(), targetMech.getY()).getHeatDissipationModifier();

        baseValue += groundModifier;

        if (targetPilot.hasPerk(Perks.Engineer)) {
            baseValue += 5;
        }

        baseValue += battleMap.getTerrainType().getHeatModifier();

        return baseValue;
    }
}
