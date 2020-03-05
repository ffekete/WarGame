package com.mygdx.wargame.battle.rules.calculator.hitchance;

import com.mygdx.wargame.common.component.targeting.TargetingModule;
import com.mygdx.wargame.common.component.weapon.Status;
import com.mygdx.wargame.common.component.weapon.Weapon;
import com.mygdx.wargame.common.component.weapon.WeaponType;
import com.mygdx.wargame.common.mech.Mech;
import com.mygdx.wargame.common.pilot.Perks;
import com.mygdx.wargame.common.pilot.Pilot;
import com.mygdx.wargame.common.pilot.Skill;
import com.mygdx.wargame.util.MathUtils;

import java.util.Optional;

public class LaserHitChanceCalculator implements HitChanceCalculator {

    @Override
    public int calculate(Pilot pilot, Mech mech, Mech target, Weapon weapon) {

        int baseHitChance = pilot.getSkills().get(Skill.Lasers) * 5;

        int weaponModifier = weapon.getAccuracy(target);
        baseHitChance += weaponModifier;

        Optional<Integer> targetingModuleModifiers = mech.getAllComponents()
                .stream()
                .filter(c -> c.getStatus() != Status.Destroyed)
                .filter(c -> TargetingModule.class.isAssignableFrom(c.getClass()))
                .map(c -> (TargetingModule) c)
                .map(c -> c.getAdditionalAccuracy(WeaponType.Laser))
                .reduce(Integer::sum);

        if (targetingModuleModifiers.isPresent()) {
            baseHitChance += targetingModuleModifiers.get();
        }

        if (pilot.hasPerk(Perks.LaserExpert)) {
            baseHitChance += 5;
        }

        baseHitChance -= (int) MathUtils.getDistance(mech.getX(), mech.getY(), target.getX(), target.getY());

        return baseHitChance;
    }
}
