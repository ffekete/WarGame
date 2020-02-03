package com.mygdx.wargame.component.weapon.laser;

import com.mygdx.wargame.component.weapon.Item;
import com.mygdx.wargame.component.weapon.Weapon;
import com.mygdx.wargame.component.weapon.WeaponType;
import com.mygdx.wargame.mech.AbstractMech;

import java.util.Optional;

@Item
public class ExtendedRangeSmallLaser implements Weapon {
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
        return 9;
    }

    @Override
    public WeaponType getType() {
        return WeaponType.Laser;
    }

    @Override
    public String getName() {
        return "Extended range small laser";
    }

    @Override
    public int getPrice() {
        return 1500;
    }

    @Override
    public int getHeat() {
        return 10;
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
        return 0;
    }

    @Override
    public int getAdditionalHeatToEnemy() {
        return 5;
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
    public float getRarity() {
        return 0.8f;
    }

    @Override
    public int getPowerConsumption() {
        return 5;
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
