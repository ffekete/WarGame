package com.mygdx.wargame.common.component.targeting;

import com.mygdx.wargame.common.component.weapon.Status;
import com.mygdx.wargame.common.component.weapon.WeaponType;

public class MissileTargetingModuleMk2 implements TargetingModule {

    private Status status = Status.Selected;

    @Override
    public int getAdditionalAccuracy(WeaponType weaponType) {
        if (weaponType == WeaponType.Missile) {
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
        return 0.1f;
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
        return 2000;
    }

    @Override
    public String getName() {
        return "Missile targeting module Mk2";
    }

    @Override
    public int getWeight() {
        return 10;
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
