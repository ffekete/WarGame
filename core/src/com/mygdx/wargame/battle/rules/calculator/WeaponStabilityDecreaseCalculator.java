package com.mygdx.wargame.battle.rules.calculator;

import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.battle.map.TerrainType;
import com.mygdx.wargame.common.component.weapon.Weapon;
import com.mygdx.wargame.common.mech.Mech;
import com.mygdx.wargame.common.pilot.Perks;
import com.mygdx.wargame.common.pilot.Pilot;
import com.mygdx.wargame.common.pilot.Skill;

import java.util.Random;

public class WeaponStabilityDecreaseCalculator {

    private CriticalHitChanceCalculator criticalHitChanceCalculator;

    public WeaponStabilityDecreaseCalculator(CriticalHitChanceCalculator criticalHitChanceCalculator) {
        this.criticalHitChanceCalculator = criticalHitChanceCalculator;
    }

    public int calculate(Pilot targetingPilot, Mech TargetingMech, Pilot targetPilot, Mech TargetMech, BattleMap battleMap, Weapon targetingWeapon) {

        boolean isCritical = new Random().nextInt(100) < criticalHitChanceCalculator.calculate(targetingPilot, TargetingMech, targetingWeapon);

        int baseStabilityDamage = targetingWeapon.getStabilityHit() * (isCritical ? 2 : 1);

        baseStabilityDamage -= targetPilot.getSkills().get(Skill.Entrenching);

        baseStabilityDamage -= TargetMech.getStabilityResistance();

        if (targetPilot.hasPerk(Perks.RockSteady)) {
            baseStabilityDamage -= 5;
        }

        baseStabilityDamage += battleMap.getTerrainType().getStabilityModifier();

        if (battleMap.getTerrainType() == TerrainType.Swamp && targetPilot.hasPerk(Perks.SwampExpert)) {
            baseStabilityDamage += 5;
        }

        if (battleMap.getTerrainType() == TerrainType.Snow && targetPilot.hasPerk(Perks.SnowExpert)) {
            baseStabilityDamage += 5;
        }

        return Math.max(baseStabilityDamage, 0);

    }

}
