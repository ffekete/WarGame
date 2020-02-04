package com.mygdx.wargame.component.reactor;

import com.mygdx.wargame.component.weapon.Item;

@Item
public class SmallFusionReactor implements Reactor {

    @Override
    public int getPowerLevel() {
        return 50;
    }

    @Override
    public float getRarity() {
        return 1f;
    }

    @Override
    public int getSlotSize() {
        return 10;
    }

    @Override
    public int getPowerConsumption() {
        return 0;
    }

    @Override
    public int getPrice() {
        return 10000;
    }

    @Override
    public String getName() {
        return "Small fusion reactor";
    }

    @Override
    public int getWeight() {
        return 30;
    }

    @Override
    public void update() {

    }
}