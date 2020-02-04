package com.mygdx.wargame.component.weapon.missile;

import com.mygdx.wargame.component.weapon.Item;
import com.mygdx.wargame.component.weapon.Weapon;
import com.mygdx.wargame.component.weapon.WeaponType;
import com.mygdx.wargame.mech.Mech;

import java.util.Optional;

@Item
public class SwarmMissile implements Weapon {
    private static final int MAX_AMMO = 80;
    private int ammo = MAX_AMMO;

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
        return 6;
    }

    @Override
    public WeaponType getType() {
        return WeaponType.Missile;
    }

    @Override
    public String getName() {
        return "Swarm missile launcher";
    }

    @Override
    public int getPrice() {
        return 3000;
    }

    @Override
    public int getHeat() {
        return 0;
    }

    @Override
    public int getWeight() {
        return 30;
    }

    @Override
    public int getSlotSize() {
        return 6;
    }

    @Override
    public int getAccuracy(Mech target) {
        return 0;
    }

    @Override
    public int getAdditionalHeatToEnemy() {
        return 4;
    }

    @Override
    public int getStabilityHit() {
        return 5;
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
        return 0.5f;
    }

    @Override
    public int getPowerConsumption() {
        return 0;
    }

    @Override
    public void update() {

    }
}
