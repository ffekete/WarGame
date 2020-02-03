package com.mygdx.wargame.component.weapon.missile;

import com.mygdx.wargame.component.weapon.Weapon;
import com.mygdx.wargame.component.weapon.WeaponType;
import com.mygdx.wargame.mech.AbstractMech;

import java.util.Optional;

public class HeatSeekingMissileMissile implements Weapon {
    private static final int MAX_AMMO = 80;
    private int ammo = MAX_AMMO;

    @Override
    public int getShieldDamage() {
        return 2;
    }

    @Override
    public int getArmorDamage() {
        return 2;
    }

    @Override
    public int getBodyDamage() {
        return 2;
    }

    @Override
    public int getRange() {
        return 8;
    }

    @Override
    public WeaponType getType() {
        return WeaponType.Missile;
    }

    @Override
    public String getName() {
        return "Heat seeking missile";
    }

    @Override
    public int getPrice() {
        return 5000;
    }

    @Override
    public int getHeat() {
        return 0;
    }

    @Override
    public int getWeight() {
        return 30;
    }

    @Override
    public int getSlot() {
        return 5;
    }

    @Override
    public int getAccuracy(AbstractMech target) {
        return target.getHeatLevel() / 10; // max 10
    }

    @Override
    public int getAdditionalHeatToEnemy() {
        return 5;
    }

    @Override
    public int getStabilityHit() {
        return 3;
    }

    @Override
    public int getCriticalChance() {
        return 2;
    }

    @Override
    public int getDamageMultiplier() {
        return 3;
    }

    @Override
    public boolean canTarget() {
        return false;
    }

    @Override
    public boolean requiresLineOfSight() {
        return false;
    }

    @Override
    public Optional<Integer> getAmmo() {
        return Optional.of(ammo);
    }

    @Override
    public Optional<Integer> getMaxAmmo() {
        return Optional.of(MAX_AMMO);
    }

    @Override
    public void resetAmmo() {
        this.ammo = MAX_AMMO;
    }

    @Override
    public float getRarity() {
        return 0.05f;
    }

    @Override
    public int getPowerConsumption() {
        return 5;
    }
}
