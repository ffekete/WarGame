package com.mygdx.wargame.rules.calculator;

import com.mygdx.wargame.component.targeting.TargetingModule;
import com.mygdx.wargame.component.weapon.Status;
import com.mygdx.wargame.component.weapon.Weapon;
import com.mygdx.wargame.mech.Mech;
import com.mygdx.wargame.pilot.Perks;
import com.mygdx.wargame.pilot.Pilot;

import java.util.Optional;

public class CriticalHitChanceCalculator {
    public int calculate(Pilot targetingPilot, Mech TargetingMech, Weapon weapon) {

        // Weapon
        int baseCriticalChance = weapon.getCriticalChance();

        // Perks
        if (targetingPilot.hasPerk(Perks.Lucky)) {
            baseCriticalChance += 3;
        }

        // Targeting modules
        Optional<Integer> additionalCriticalChance = TargetingMech.getAllComponents()
                .stream()
                .filter(c -> c.getStatus() != Status.Destroyed)
                .filter(c -> TargetingModule.class.isAssignableFrom(c.getClass()))
                .map(c -> ((TargetingModule) c).getAdditionalCriticalChance())
                .reduce(Integer::sum);

        if (additionalCriticalChance.isPresent())
            baseCriticalChance += additionalCriticalChance.get();

        return baseCriticalChance;
    }
}
