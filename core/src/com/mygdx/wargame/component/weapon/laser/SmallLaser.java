package com.mygdx.wargame.component.weapon.laser;

import com.mygdx.wargame.component.weapon.Item;
import com.mygdx.wargame.component.weapon.Weapon;
import com.mygdx.wargame.component.weapon.WeaponType;
import com.mygdx.wargame.mech.AbstractMech;

import java.util.Optional;

@Item
public class SmallLaser implements Weapon {
    @Override
    public int getShieldDamage() {
        return 5;
    }

    @Override
    public int getArmorDamage() {
        return 10;
    }

    @Override
    public int getStructuralDamage() {
        return 10;
    }

    @Override
    public int getRange() {
        return 7;
    }

    @Override
    public WeaponType getType() {
        return WeaponType.Laser;
    }

    @Override
    public String getName() {
        return "Small laser";
    }

    @Override
    public int getPrice() {
        return 1000;
    }

    @Override
    public int getHeat() {
        return 10;
    }

    @Override
    public int getWeight() {
        return 10;
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
        return 1;
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
