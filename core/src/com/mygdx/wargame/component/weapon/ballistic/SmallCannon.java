package com.mygdx.wargame.component.weapon.ballistic;

import com.mygdx.wargame.component.weapon.Item;
import com.mygdx.wargame.component.weapon.Status;
import com.mygdx.wargame.component.weapon.Weapon;
import com.mygdx.wargame.component.weapon.WeaponType;
import com.mygdx.wargame.mech.Mech;

import java.util.Optional;

@Item
public class SmallCannon implements Weapon {
    private static final int MAX_AMMO = 20;
    private int ammo = MAX_AMMO;
    private Status status;


    @Override
    public int getShieldDamage() {
        return 0;
    }

    @Override
    public int getArmorDamage() {
        return 4;
    }

    @Override
    public int getBodyDamage() {
        return 6;
    }

    @Override
    public int getRange() {
        return 7;
    }

    @Override
    public WeaponType getType() {
        return WeaponType.Ballistic;
    }

    @Override
    public String getName() {
        return "Small cannon";
    }

    @Override
    public int getPrice() {
        return 1000;
    }

    @Override
    public int getHeat() {
        return 0;
    }

    @Override
    public int getWeight() {
        return 12;
    }

    @Override
    public int getSlotSize() {
        return 3;
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
        return 4;
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
        return 1f;
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
