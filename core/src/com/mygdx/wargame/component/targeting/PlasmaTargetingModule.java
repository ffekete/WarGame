package com.mygdx.wargame.component.targeting;

import com.mygdx.wargame.component.weapon.WeaponType;

public class PlasmaTargetingModule implements TargetingModule {

    @Override
    public int getAdditionalAccuracy(WeaponType weaponType) {
        if(weaponType == WeaponType.Plasma) {
            return 5;
        }
        return 0;
    }

    @Override
    public int getAdditionalCriticalChance() {
        return 0;
    }

    @Override
    public float getRarity() {
        return 0.2f;
    }

    @Override
    public int getSlotSize() {
        return 2;
    }

    @Override
    public int getPowerConsumption() {
        return 10;
    }

    @Override
    public int getPrice() {
        return 1000;
    }

    @Override
    public String getName() {
        return "Plasma targeting module";
    }

    @Override
    public int getWeight() {
        return 10;
    }

    @Override
    public void update() {

    }
}
