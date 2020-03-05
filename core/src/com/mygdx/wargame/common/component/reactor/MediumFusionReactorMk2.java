package com.mygdx.wargame.common.component.reactor;

import com.mygdx.wargame.common.component.weapon.Item;
import com.mygdx.wargame.common.component.weapon.Status;

@Item
public class MediumFusionReactorMk2 implements Reactor {

    private Status status;

    @Override
    public int getPowerLevel() {
        return 80;
    }

    @Override
    public float getRarity() {
        return 0.4f;
    }

    @Override
    public int getSlotSize() {
        return 15;
    }

    @Override
    public int getPowerConsumption() {
        return 0;
    }

    @Override
    public int getPrice() {
        return 18000;
    }

    @Override
    public String getName() {
        return "Medium fusion reactor Mk2";
    }

    @Override
    public int getWeight() {
        return 40;
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
