package com.mygdx.wargame.battle.rules.calculator;

import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.battle.map.overlay.TileOverlayType;
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

        if(battleMap.getNodeGraphLv1().getNodeWeb()[(int)targetMech.getX()][(int)targetMech.getY()].getGroundOverlay() != null &&
                battleMap.getNodeGraphLv1().getNodeWeb()[(int)targetMech.getX()][(int)targetMech.getY()].getGroundOverlay().getTileOverlayType() == TileOverlayType.Water) {
            baseValue += 20;
        }

        baseValue -= battleMap.getFireMap()[(int) targetMech.getX()][(int) targetMech.getY()] > 0 ? 20 : 0;

        if (targetPilot.hasPerk(Perks.Engineer)) {
            baseValue += 5;
        }

        baseValue += battleMap.getTerrainType().getHeatModifier();

        return baseValue;
    }
}
