package com.mygdx.wargame.common.component.weapon.plasma;

import com.mygdx.wargame.common.component.weapon.Item;
import com.mygdx.wargame.common.component.weapon.Status;
import com.mygdx.wargame.common.component.weapon.Weapon;
import com.mygdx.wargame.common.component.weapon.WeaponType;
import com.mygdx.wargame.common.mech.Mech;

import java.util.Optional;

@Item
public class PlasmaGunMk3 implements Weapon {
    private static final int MAX_AMMO = 20;
    private int ammo = MAX_AMMO;
    private Status status = Status.Selected;

    @Override
    public String getShortName() {
        return "PlaG3";
    }

    @Override
    public int getShieldDamage() {
        return 0;
    }

    @Override
    public int getArmorDamage() {
        return 9;
    }

    @Override
    public int getBodyDamage() {
        return 5;
    }

    @Override
    public int getRange() {
        return 8;
    }

    @Override
    public WeaponType getType() {
        return WeaponType.Plasma;
    }

    @Override
    public String getName() {
        return "Plasma gun";
    }

    @Override
    public int getPrice() {
        return 2500;
    }

    @Override
    public int getHeat() {
        return 12;
    }

    @Override
    public int getWeight() {
        return 12;
    }

    @Override
    public int getSlotSize() {
        return 2;
    }

    @Override
    public int getAccuracy(Mech target) {
        return 0;
    }

    @Override
    public int getAdditionalHeatToEnemy() {
        return 7;
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
        return 0.6f;
    }

    @Override
    public int getPowerConsumption() {
        return 7;
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
