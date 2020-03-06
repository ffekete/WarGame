package com.mygdx.wargame.battle.rules.calculator;

import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.battle.map.TerrainType;
import com.mygdx.wargame.battle.map.overlay.TileOverlayType;
import com.mygdx.wargame.common.component.weapon.Weapon;
import com.mygdx.wargame.common.mech.Mech;
import com.mygdx.wargame.common.pilot.Perks;
import com.mygdx.wargame.common.pilot.Pilot;
import com.mygdx.wargame.common.pilot.Skill;

import java.util.Random;

public class StabilityDecreaseCalculator {

    public int calculate(Mech targetMech, BattleMap battleMap) {

        int baseValue = 50;

        if (battleMap.getNodeGraphLv1().getNodeWeb()[(int) targetMech.getX()][(int) targetMech.getY()].getGroundOverlay() != null &&
                battleMap.getNodeGraphLv1().getNodeWeb()[(int) targetMech.getX()][(int) targetMech.getY()].getGroundOverlay().getTileOverlayType() != null
        ) {
            baseValue += battleMap.getNodeGraphLv1().getNodeWeb()[(int) targetMech.getX()][(int) targetMech.getY()].getGroundOverlay().getTileOverlayType().getStabilityModifier();
        }
        return Math.min(targetMech.getStability() + baseValue, 100);
    }

}
