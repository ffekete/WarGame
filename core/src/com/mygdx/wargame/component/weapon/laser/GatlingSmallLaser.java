package com.mygdx.wargame.component.weapon.laser;

import com.mygdx.wargame.component.weapon.Item;
import com.mygdx.wargame.component.weapon.Status;
import com.mygdx.wargame.component.weapon.Weapon;
import com.mygdx.wargame.component.weapon.WeaponType;
import com.mygdx.wargame.mech.Mech;

import java.util.Optional;

@Item
public class GatlingSmallLaser implements Weapon {

    private Status status;

    @Override
    public String getShortName() {
        return "GaSLa";
    }

    @Override
    public int getShieldDamage() {
        return 3;
    }

    @Override
    public int getArmorDamage() {
        return 6;
    }

    @Override
    public int getBodyDamage() {
        return 6;
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
        return "Gatling small laser";
    }

    @Override
    public int getPrice() {
        return 3000;
    }

    @Override
    public int getHeat() {
        return 20;
    }

    @Override
    public int getWeight() {
        return 15;
    }

    @Override
    public int getSlotSize() {
        return 3;
    }


    @Override
    public int getAccuracy(Mech target) {
        return -10;
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
        return 5;
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
        return 45;
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
