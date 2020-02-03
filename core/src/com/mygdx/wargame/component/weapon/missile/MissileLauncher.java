package com.mygdx.wargame.component.weapon.missile;

import com.mygdx.wargame.component.weapon.Item;
import com.mygdx.wargame.component.weapon.Weapon;
import com.mygdx.wargame.component.weapon.WeaponType;
import com.mygdx.wargame.mech.AbstractMech;

import java.util.Optional;

@Item
public class MissileLauncher implements Weapon {
    private static final int MAX_AMMO = 100;
    private int ammo = MAX_AMMO;

    @Override
    public int getShieldDamage() {
        return 3;
    }

    @Override
    public int getArmorDamage() {
        return 3;
    }

    @Override
    public int getBodyDamage() {
        return 3;
    }

    @Override
    public int getRange() {
        return 8;
    }

    @Override
    public WeaponType getType() {
        return WeaponType.Missile;
    }

    @Override
    public String getName() {
        return "Missile launcher";
    }

    @Override
    public int getPrice() {
        return 2000;
    }

    @Override
    public int getHeat() {
        return 0;
    }

    @Override
    public int getWeight() {
        return 20;
    }

    @Override
    public int getSlotSize() {
        return 4;
    }

    @Override
    public int getAccuracy(AbstractMech target) {
        return 0;
    }

    @Override
    public int getAdditionalHeatToEnemy() {
        return 3;
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
        return 3;
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
        return 1;
    }

    @Override
    public int getPowerConsumption() {
        return 0;
    }

    @Override
    public void update() {

    }
}
