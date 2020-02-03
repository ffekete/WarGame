package com.mygdx.wargame.component.weapon.laser;

import com.mygdx.wargame.component.weapon.Item;
import com.mygdx.wargame.component.weapon.Weapon;
import com.mygdx.wargame.component.weapon.WeaponType;
import com.mygdx.wargame.mech.AbstractMech;

import java.util.Optional;

@Item
public class DoubleBarrelMediumLaser implements Weapon {

    @Override
    public int getShieldDamage() {
        return 5;
    }

    @Override
    public int getArmorDamage() {
        return 10;
    }

    @Override
    public int getBodyDamage() {
        return 10;
    }

    @Override
    public int getRange() {
        return 6;
    }

    @Override
    public WeaponType getType() {
        return WeaponType.Laser;
    }

    @Override
    public String getName() {
        return "Double barrel medium laser";
    }

    @Override
    public int getPrice() {
        return 4000;
    }

    @Override
    public int getHeat() {
        return 30;
    }

    @Override
    public int getWeight() {
        return 25;
    }

    @Override
    public int getSlotSize() {
        return 3;
    }

    @Override
    public int getAccuracy(AbstractMech target) {
        return 10;
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
        return 2;
    }

    @Override
    public boolean canTarget() {
        return false;
    }

    @Override
    public float getRarity() {
        return 0.05f;
    }

    @Override
    public int getPowerConsumption() {
        return 30;
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
}
