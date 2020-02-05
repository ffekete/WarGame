package com.mygdx.wargame.component.reactor;

import com.mygdx.wargame.component.weapon.Item;
import com.mygdx.wargame.component.weapon.Status;

@Item
public class SmallFusionReactorMk3 implements Reactor {


    private Status status;

    @Override
    public int getPowerLevel() {
        return 55;
    }

    @Override
    public float getRarity() {
        return 1f;
    }

    @Override
    public int getSlotSize() {
        return 8;
    }

    @Override
    public int getPowerConsumption() {
        return 0;
    }

    @Override
    public int getPrice() {
        return 14000;
    }

    @Override
    public String getName() {
        return "Small fusion reactor Mk3";
    }

    @Override
    public int getWeight() {
        return 27;
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
