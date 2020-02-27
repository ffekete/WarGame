package com.mygdx.wargame.component.targeting;

import com.mygdx.wargame.component.weapon.Status;
import com.mygdx.wargame.component.weapon.WeaponType;

public class IonTargetingModuleMk3 implements TargetingModule {

    private Status status;

    @Override
    public int getAdditionalAccuracy(WeaponType weaponType) {
        if (weaponType == WeaponType.Ion) {
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
        return "Ion targeting module Mk3";
    }

    @Override
    public int getWeight() {
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
}
