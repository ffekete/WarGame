package com.mygdx.wargame.component.heatsink;

public class PassiveHeatSinkMk2 implements HeatSink {
    @Override
    public int getHeatDissipation() {
        return 20;
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
        return 0;
    }

    @Override
    public int getPrice() {
        return 600;
    }

    @Override
    public String getName() {
        return "Passive heat sink Mk2";
    }

    @Override
    public int getWeight() {
        return 1;
    }

    @Override
    public void update() {

    }
}
