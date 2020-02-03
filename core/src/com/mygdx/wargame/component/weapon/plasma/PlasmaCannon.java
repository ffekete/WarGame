package com.mygdx.wargame.component.weapon.plasma;

import com.mygdx.wargame.component.weapon.Weapon;
import com.mygdx.wargame.component.weapon.WeaponType;
import com.mygdx.wargame.mech.AbstractMech;

import java.util.Optional;

public class PlasmaCannon implements Weapon {
    private static final int MAX_AMMO = 10;
    private int ammo = MAX_AMMO;

    @Override
    public int getShieldDamage() {
        return 0;
    }

    @Override
    public int getArmorDamage() {
        return 10;
    }

    @Override
    public int getBodyDamage() {
        return 6;
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
        return "Plasma cannon";
    }

    @Override
    public int getPrice() {
        return 2500;
    }

    @Override
    public int getHeat() {
        return 20;
    }

    @Override
    public int getWeight() {
        return 25;
    }

    @Override
    public int getSlot() {
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
        return 3;
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
        return 0.5f;
    }

    @Override
    public int getPowerConsumption() {
        return 15;
    }
}
