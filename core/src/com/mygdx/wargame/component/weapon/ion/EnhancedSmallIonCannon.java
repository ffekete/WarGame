package com.mygdx.wargame.component.weapon.ion;

import com.mygdx.wargame.component.weapon.Weapon;
import com.mygdx.wargame.component.weapon.WeaponType;
import com.mygdx.wargame.mech.AbstractMech;

import java.util.Optional;

public class EnhancedSmallIonCannon implements Weapon {

    @Override
    public int getShieldDamage() {
        return 17;
    }

    @Override
    public int getArmorDamage() {
        return 0;
    }

    @Override
    public int getStructuralDamage() {
        return 0;
    }

    @Override
    public int getRange() {
        return 7;
    }

    @Override
    public WeaponType getType() {
        return WeaponType.Ion;
    }

    @Override
    public String getName() {
        return "Enhanced small ion cannon";
    }

    @Override
    public int getPrice() {
        return 2500;
    }

    @Override
    public int getHeat() {
        return 12;
    }

    @Override
    public int getWeight() {
        return 12;
    }

    @Override
    public int getSlot() {
        return 1;
    }

    @Override
    public int getAccuracy(AbstractMech target) {
        return 5;
    }

    @Override
    public int getAdditionalHeatToEnemy() {
        return 0;
    }

    @Override
    public int getStabilityHit() {
        return 0;
    }

    @Override
    public int getCriticalChance() {
        return 6;
    }

    @Override
    public int getDamageMultiplier() {
        return 1;
    }

    @Override
    public boolean canTarget() {
        return false;
    }

    @Override
    public boolean requiresLineOfSight() {
        return true;
    }

    @Override
    public Optional<Integer> getAmmo() {
        return Optional.empty();
    }

    @Override
    public float getRarity() {
        return 0.1f;
    }

    @Override
    public int getPowerConsumption() {
        return 12;
    }

    @Override
    public Optional<Integer> getMaxAmmo() {
        return Optional.empty();
    }

    @Override
    public void resetAmmo() {

    }
}
