package com.mygdx.wargame.component.weapon.ballistic;

import com.mygdx.wargame.component.weapon.Weapon;
import com.mygdx.wargame.component.weapon.WeaponType;
import com.mygdx.wargame.mech.AbstractMech;

import java.util.Optional;

public class GatlingGun implements Weapon {

    private static final int MAX_AMMO = 600;
    private int ammo = MAX_AMMO;

    @Override
    public int getShieldDamage() {
        return 0;
    }

    @Override
    public int getArmorDamage() {
        return 1;
    }

    @Override
    public int getStructuralDamage() {
        return 1;
    }

    @Override
    public int getRange() {
        return 5;
    }

    @Override
    public WeaponType getType() {
        return WeaponType.Ballistic;
    }

    @Override
    public String getName() {
        return "Gatling gun";
    }

    @Override
    public int getPrice() {
        return 2500;
    }

    @Override
    public int getHeat() {
        return 0;
    }

    @Override
    public int getWeight() {
        return 40;
    }

    @Override
    public int getSlot() {
        return 4;
    }

    @Override
    public int getAccuracy(AbstractMech target) {
        return -10;
    }

    @Override
    public int getAdditionalHeatToEnemy() {
        return 0;
    }

    @Override
    public int getStabilityHit() {
        return 1;
    }

    @Override
    public int getCriticalChance() {
        return 1;
    }

    @Override
    public int getDamageMultiplier() {
        return 10;
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
    public Optional<Integer> getMaxAmmo() {
        return Optional.empty();
    }

    @Override
    public void resetAmmo() {

    }

    @Override
    public float getRarity() {
        return 0;
    }

    @Override
    public int getPowerConsumption() {
        return 0;
    }
}
