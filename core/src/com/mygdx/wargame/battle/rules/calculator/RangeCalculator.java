package com.mygdx.wargame.battle.rules.calculator;

import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.common.component.weapon.Weapon;
import com.mygdx.wargame.common.mech.Mech;
import com.mygdx.wargame.common.pilot.Perks;
import com.mygdx.wargame.common.pilot.Pilot;

import java.util.Comparator;
import java.util.Optional;

public class RangeCalculator {

    public int calculate(Pilot pilot, Weapon weapon) {
        int baseRange = weapon.getRange();

        if (pilot.hasPerk(Perks.Cautious)) {
            baseRange++;
        }

        return baseRange;
    }

    public int calculateAllWeaponsRange(Pilot targetingPilot, Mech targetingMech, BattleMap battleMap) {
        if(!targetingMech.isRangedAttack()) {
            return 1;
        }
        Optional<Integer> range = targetingMech.getSelectedWeapons()
                .stream()
                .map(w -> calculate(targetingPilot, w))
                .min(new Comparator<Integer>() {
                    @Override
                    public int compare(Integer o1, Integer o2) {
                        return Integer.compare(o1, o2);
                    }
                });

        return range.map(r -> r + battleMap.getNodeGraph().getNodeWeb()[(int)targetingMech.getX()][(int)targetingMech.getY()].getTile().getRangeModifier()).orElse(0);
    }
}
