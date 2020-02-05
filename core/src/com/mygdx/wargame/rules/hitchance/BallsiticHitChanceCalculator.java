package com.mygdx.wargame.rules.hitchance;

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

public class BallsiticHitChanceCalculator implements HitChanceCalculator {

    @Override
    public int calculate(Pilot pilot, Mech mech, Mech target, Weapon weapon) {

        int baseHitChance = pilot.getSkills().get(Skill.Ballistics) * 5;

        int weaponModifier = weapon.getAccuracy(target);
        baseHitChance += weaponModifier;

        Optional<Integer> targetingModuleModifiers = mech.getAllComponents()
                .stream()
                .filter(c -> c.getStatus() != Status.Destroyed)
                .filter(c -> TargetingModule.class.isAssignableFrom(c.getClass()))
                .map(c -> (TargetingModule) c)
                .map(c -> c.getAdditionalAccuracy(WeaponType.Ballistic))
                .reduce(Integer::sum);

        if (targetingModuleModifiers.isPresent()) {
            baseHitChance += targetingModuleModifiers.get();
        }

        if (pilot.hasPerk(Perks.BallisticsExpert)) {
            baseHitChance += 5;
        }

        baseHitChance -= (int)MathUtils.getDistance(mech.getX(), mech.getY(), target.getX(), target.getY());

        return baseHitChance;
    }
}
