package com.mygdx.wargame.component.weapon.ion;

import com.mygdx.wargame.component.weapon.Item;
import com.mygdx.wargame.component.weapon.Weapon;
import com.mygdx.wargame.component.weapon.WeaponType;
import com.mygdx.wargame.mech.AbstractMech;

import java.util.Optional;

@Item
public class ExtendedRangeMediumIonCannon implements Weapon {

    @Override
    public int getShieldDamage() {
        return 20;
    }

    @Override
    public int getArmorDamage() {
        return 0;
    }

    @Override
    public int getBodyDamage() {
        return 0;
    }

    @Override
    public int getRange() {
        return 11;
    }

    @Override
    public WeaponType getType() {
        return WeaponType.Ion;
    }

    @Override
    public String getName() {
        return "Extended range medium ion cannon";
    }

    @Override
    public int getPrice() {
        return 3000;
    }

    @Override
    public int getHeat() {
        return 17;
    }

    @Override
    public int getWeight() {
        return 17;
    }

    @Override
    public int getSlotSize() {
        return 2;
    }

    @Override
    public int getAccuracy(AbstractMech target) {
        return 0;
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
        return 3;
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
        return 0.75f;
    }

    @Override
    public int getPowerConsumption() {
        return 17;
    }

    @Override
    public Optional<Integer> getMaxAmmo() {
        return Optional.empty();
    }

    @Override
    public void resetAmmo() {

    }

    @Override
    public void update() {

    }
}
