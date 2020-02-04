package com.mygdx.wargame.component.reactor;

import com.mygdx.wargame.component.weapon.Item;

@Item
public class LargeFusionReactor implements Reactor {

    @Override
    public int getPowerLevel() {
        return 100;
    }

    @Override
    public float getRarity() {
        return 1f;
    }

    @Override
    public int getSlotSize() {
        return 20;
    }

    @Override
    public int getPowerConsumption() {
        return 0;
    }

    @Override
    public int getPrice() {
        return 20000;
    }

    @Override
    public String getName() {
        return "Large fusion reactor";
    }

    @Override
    public int getWeight() {
        return 60;
    }

    @Override
    public void update() {

    }
}
