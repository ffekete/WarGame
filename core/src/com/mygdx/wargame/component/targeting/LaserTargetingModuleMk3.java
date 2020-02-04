package com.mygdx.wargame.component.targeting;

import com.mygdx.wargame.component.weapon.WeaponType;

public class LaserTargetingModuleMk3 implements TargetingModule {

    @Override
    public int getAdditionalAccuracy(WeaponType weaponType) {
        if(weaponType == WeaponType.Laser) {
            return 5;
        }
        return 0;
    }

    @Override
    public int getAdditionalCriticalChance() {
        return 3;
    }

    @Override
    public float getRarity() {
        return 0.05f;
    }

    @Override
    public int getSlotSize() {
        return 2;
    }

    @Override
    public int getPowerConsumption() {
        return 8;
    }

    @Override
    public int getPrice() {
        return 3000;
    }

    @Override
    public String getName() {
        return "Laser targeting module Mk3";
    }

    @Override
    public int getWeight() {
        return 5;
    }

    @Override
    public void update() {

    }
}
