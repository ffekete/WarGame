package com.mygdx.wargame.rules.calculator.hitchance;

import com.mygdx.wargame.component.targeting.TargetingModule;
import com.mygdx.wargame.component.weapon.Status;
import com.mygdx.wargame.component.weapon.Weapon;
import com.mygdx.wargame.component.weapon.WeaponType;
import com.mygdx.wargame.mech.Mech;
import com.mygdx.wargame.pilot.Perks;
import com.mygdx.wargame.pilot.Pilot;
import com.mygdx.wargame.pilot.Skill;
import com.mygdx.wargame.util.MathUtils;

import java.util.Optional;

public class BallisticHitChanceCalculator implements HitChanceCalculator {

    @Override
    public int calculate(Pilot targetingPilot, Mech targetingMech, Mech target, Weapon weapon) {

        int baseHitChance = targetingPilot.getSkills().get(Skill.Ballistics) * 5;

        int weaponModifier = weapon.getAccuracy(target);
        baseHitChance += weaponModifier;

        Optional<Integer> targetingModuleModifiers = targetingMech.getAllComponents()
                .stream()
                .filter(c -> c.getStatus() != Status.Destroyed)
                .filter(c -> TargetingModule.class.isAssignableFrom(c.getClass()))
                .map(c -> (TargetingModule) c)
                .map(c -> c.getAdditionalAccuracy(WeaponType.Ballistic))
                .reduce(Integer::sum);

        if (targetingModuleModifiers.isPresent()) {
            baseHitChance += targetingModuleModifiers.get();
        }

        if (targetingPilot.hasPerk(Perks.BallisticsExpert)) {
            baseHitChance += 5;
        }

        baseHitChance -= (int) MathUtils.getDistance(targetingMech.getX(), targetingMech.getY(), target.getX(), target.getY());

        return baseHitChance;
    }
}
