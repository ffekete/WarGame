package com.mygdx.wargame.component.weapon.ballistic;

import com.mygdx.wargame.component.weapon.Item;
import com.mygdx.wargame.component.weapon.Weapon;
import com.mygdx.wargame.component.weapon.WeaponType;
import com.mygdx.wargame.mech.AbstractMech;

import java.util.Optional;

@Item
public class LargeGatlingGun implements Weapon {

    private static final int MAX_AMMO = 800;
    private int ammo = MAX_AMMO;

    @Override
    public int getShieldDamage() {
        return 0;
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
        return 6;
    }

    @Override
    public WeaponType getType() {
        return WeaponType.Ballistic;
    }

    @Override
    public String getName() {
        return "Large gatling gun";
    }

    @Override
    public int getPrice() {
        return 5000;
    }

    @Override
    public int getHeat() {
        return 5;
    }

    @Override
    public int getWeight() {
        return 60;
    }

    @Override
    public int getSlotSize() {
        return 6;
    }

    @Override
    public int getAccuracy(AbstractMech target) {
        return -10;
    }

    @Override
    public int getAdditionalHeatToEnemy() {
        return 0;
    }

    @Override
    public int getStabilityHit() {
        return 2;
    }

    @Override
    public int getCriticalChance() {
        return 1;
    }

    @Override
    public int getDamageMultiplier() {
        return 10;
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
        return .1f;
    }

    @Override
    public int getPowerConsumption() {
        return 0;
    }
}
