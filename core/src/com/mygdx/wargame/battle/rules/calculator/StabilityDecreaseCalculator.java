package com.mygdx.wargame.battle.rules.calculator;

import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.common.mech.Mech;

public class StabilityDecreaseCalculator {

    public int calculate(Mech targetMech, BattleMap battleMap) {

        int baseValue = 50;

        return Math.min(targetMech.getStability() + baseValue, 100);
    }

}
