package com.mygdx.wargame.component.heatsink;

public class ActiveHeatSink implements HeatSink {

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
}
