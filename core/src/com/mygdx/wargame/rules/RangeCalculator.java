package com.mygdx.wargame.rules;

import com.mygdx.wargame.component.weapon.Weapon;
import com.mygdx.wargame.mech.Mech;
import com.mygdx.wargame.pilot.Perks;
import com.mygdx.wargame.pilot.Pilot;

import java.util.Comparator;
import java.util.Optional;

public class RangeCalculator {

    int calculate(Pilot pilot, Weapon weapon) {
        int baseRange = weapon.getRange();

        if(pilot.hasPerk(Perks.Cautious)) {
            baseRange++;
        }

        return baseRange;
    }

    int calculateAllWeaponsRange(Pilot pilot, Mech mech) {
        Optional<Integer> range = mech.getSelectedWeapons()
                .stream()
                .map(w -> calculate(pilot, w))
                .min(new Comparator<Integer>() {
                    @Override
                    public int compare(Integer o1, Integer o2) {
                        return Integer.compare(o1, o2);
                    }
                });

        return range.orElse(0);
    }
}
