package com.mygdx.wargame.common.component.weapon.missile;

import com.mygdx.wargame.common.component.weapon.Item;
import com.mygdx.wargame.common.component.weapon.Status;
import com.mygdx.wargame.common.component.weapon.Weapon;
import com.mygdx.wargame.common.component.weapon.WeaponType;
import com.mygdx.wargame.common.mech.Mech;

import java.util.Optional;

@Item
public class AutoTargetingMissile implements Weapon {
    private static final int MAX_AMMO = 80;
    private int ammo = MAX_AMMO;
    private Status status = Status.Selected;

    @Override
    public String getShortName() {
        return "ATM";
    }

    @Override
    public int getShieldDamage() {
        return 2;
    }

    @Override
    public int getArmorDamage() {
        return 2;
    }

    @Override
    public int getBodyDamage() {
        return 2;
    }

    @Override
    public int getRange() {
        return 7;
    }

    @Override
    public WeaponType getType() {
        return WeaponType.Missile;
    }

    @Override
    public String getName() {
        return "Auto targeting missile";
    }

    @Override
    public int getPrice() {
        return 4000;
    }

    @Override
    public int getHeat() {
        return 0;
    }

    @Override
    public int getWeight() {
        return 25;
    }

    @Override
    public int getSlotSize() {
        return 4;
    }

    @Override
    public int getAccuracy(Mech target) {
        return 10;
    }

    @Override
    public int getAdditionalHeatToEnemy() {
        return 3;
    }

    @Override
    public int getStabilityHit() {
        return 3;
    }

    @Override
    public int getCriticalChance() {
        return 1;
    }

    @Override
    public int getDamageMultiplier() {
        return 4;
    }

    @Override
    public boolean canTarget() {
        return false;
    }

    @Override
    public boolean requiresLineOfSight() {
        return false;
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
        return 0.15f;
    }

    @Override
    public int getPowerConsumption() {
        return 5;
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
        this.ammo--;
    }
}
