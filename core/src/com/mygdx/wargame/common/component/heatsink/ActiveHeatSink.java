package com.mygdx.wargame.common.component.heatsink;

import com.mygdx.wargame.common.component.weapon.Status;

public class ActiveHeatSink implements HeatSink {
    private Status status;

    @Override
    public int getHeatDissipation() {
        return 30;
    }

    @Override
    public float getRarity() {
        return 1f;
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
        return 200;
    }

    @Override
    public String getName() {
        return "Active heat sink";
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
