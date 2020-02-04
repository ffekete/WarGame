package com.mygdx.wargame.component.heatsink;

public class ActiveHeatSinkMk2 implements HeatSink {

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
}
