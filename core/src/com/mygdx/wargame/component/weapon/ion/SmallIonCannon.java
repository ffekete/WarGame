package com.mygdx.wargame.component.weapon.ion;

import com.mygdx.wargame.component.weapon.Item;
import com.mygdx.wargame.component.weapon.Status;
import com.mygdx.wargame.component.weapon.Weapon;
import com.mygdx.wargame.component.weapon.WeaponType;
import com.mygdx.wargame.mech.Mech;

import java.util.Optional;

@Item
public class SmallIonCannon implements Weapon {

    private Status status;

    @Override
    public String getShortName() {
        return "SIon";
    }

    @Override
    public int getShieldDamage() {
        return 15;
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
        return 7;
    }

    @Override
    public WeaponType getType() {
        return WeaponType.Ion;
    }

    @Override
    public String getName() {
        return "Small ion cannon";
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
    public int getSlotSize() {
        return 1;
    }

    @Override
    public int getAccuracy(Mech target) {
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
        return 1;
    }

    @Override
    public int getPowerConsumption() {
        return 10;
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


    @Override
    public Status getStatus() {
        return status;
    }

    @Override
    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public void reduceAmmo() {

    }
}
