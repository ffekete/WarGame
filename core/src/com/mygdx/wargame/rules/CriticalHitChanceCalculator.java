package com.mygdx.wargame.rules;

import com.mygdx.wargame.component.targeting.TargetingModule;
import com.mygdx.wargame.component.weapon.Weapon;
import com.mygdx.wargame.mech.Mech;
import com.mygdx.wargame.pilot.Perks;
import com.mygdx.wargame.pilot.Pilot;

import java.util.Optional;

public class CriticalHitChanceCalculator {
    int calculate(Pilot pilot, Mech mech, Weapon weapon) {

        // Weapon
        int baseCriticalChance = weapon.getCriticalChance();

        // Perks
        if (pilot.hasPerk(Perks.Lucky)) {
            baseCriticalChance += 3;
        }

        // Targeting modules
        Optional<Integer> additionalCriticalChance = mech.getAllComponents()
                .stream()
                .filter(c -> TargetingModule.class.isAssignableFrom(c.getClass()))
                .map(c -> ((TargetingModule)c).getAdditionalCriticalChance())
                .reduce(Integer::sum);

        if(additionalCriticalChance.isPresent())
            baseCriticalChance += additionalCriticalChance.get();

        return baseCriticalChance;
    }
}
