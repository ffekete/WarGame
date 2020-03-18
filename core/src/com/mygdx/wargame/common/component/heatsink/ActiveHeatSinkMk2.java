package com.mygdx.wargame.common.component.heatsink;

import com.mygdx.wargame.common.component.weapon.Status;

public class ActiveHeatSinkMk2 implements HeatSink {

    private Status status = Status.Selected;

    @Override
    public int getHeatDissipation() {
        return 35;
    }

    @Override
    public float getRarity() {
        return 0.3f;
    }

    @Override
    public int getSlotSize() {
        return 1;
    }

    @Override
    public int getPowerConsumption() {
        return 5;
    }

    @Override
    public int getPrice() {
        return 600;
    }

    @Override
    public String getName() {
        return "Active heat sink Mk2";
    }

    @Override
    public int getWeight() {
        return 2;
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
