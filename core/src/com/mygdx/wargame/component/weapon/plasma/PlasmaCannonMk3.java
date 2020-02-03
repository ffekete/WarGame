package com.mygdx.wargame.component.weapon.plasma;

import com.mygdx.wargame.component.weapon.Item;
import com.mygdx.wargame.component.weapon.Weapon;
import com.mygdx.wargame.component.weapon.WeaponType;
import com.mygdx.wargame.mech.AbstractMech;

import java.util.Optional;

@Item
public class PlasmaCannonMk3 implements Weapon {
    private static final int MAX_AMMO = 15;
    private int ammo = MAX_AMMO;

    @Override
    public int getShieldDamage() {
        return 0;
    }

    @Override
    public int getArmorDamage() {
        return 12;
    }

    @Override
    public int getBodyDamage() {
        return 7;
    }

    @Override
    public int getRange() {
        return 10;
    }

    @Override
    public WeaponType getType() {
        return WeaponType.Plasma;
    }

    @Override
    public String getName() {
        return "Plasma cannon Mk3";
    }

    @Override
    public int getPrice() {
        return 5500;
    }

    @Override
    public int getHeat() {
        return 20;
    }

    @Override
    public int getWeight() {
        return 20;
    }

    @Override
    public int getSlotSize() {
        return 3;
    }

    @Override
    public int getAccuracy(AbstractMech target) {
        return 0;
    }

    @Override
    public int getAdditionalHeatToEnemy() {
        return 10;
    }

    @Override
    public int getStabilityHit() {
        return 0;
    }

    @Override
    public int getCriticalChance() {
        return 5;
    }

    @Override
    public int getDamageMultiplier() {
        return 1;
    }

    @Override
    public boolean canTarget() {
        return true;
    }

    @Override
    public boolean requiresLineOfSight() {
        return true;
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
        return 0.1f;
    }

    @Override
    public int getPowerConsumption() {
        return 12;
    }
}