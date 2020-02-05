package com.mygdx.wargame.component.weapon.flamer;

import com.mygdx.wargame.component.weapon.Item;
import com.mygdx.wargame.component.weapon.Status;
import com.mygdx.wargame.component.weapon.Weapon;
import com.mygdx.wargame.component.weapon.WeaponType;
import com.mygdx.wargame.mech.Mech;

import java.util.Optional;

@Item
public class Flamer implements Weapon {

    public static final int MAX_AMMO = 10;

    private int ammo;

    private Status status;


    @Override
    public int getShieldDamage() {
        return 5;
    }

    @Override
    public int getArmorDamage() {
        return 0;
    }

    @Override
    public int getBodyDamage() {
        return 5;
    }

    @Override
    public int getRange() {
        return 4;
    }

    @Override
    public WeaponType getType() {
        return WeaponType.Flamer;
    }

    @Override
    public String getName() {
        return "Flamer";
    }

    @Override
    public int getPrice() {
        return 1000;
    }

    @Override
    public int getHeat() {
        return 5;
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
        return 10;
    }

    @Override
    public int getAdditionalHeatToEnemy() {
        return 30;
    }

    @Override
    public int getStabilityHit() {
        return 0;
    }

    @Override
    public int getCriticalChance() {
        return 0;
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
        return 0;
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
}
