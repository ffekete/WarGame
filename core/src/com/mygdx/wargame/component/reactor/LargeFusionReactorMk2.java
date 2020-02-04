package com.mygdx.wargame.component.reactor;

import com.mygdx.wargame.component.weapon.Item;

@Item
public class LargeFusionReactorMk2 implements Reactor {

    @Override
    public int getPowerLevel() {
        return 110;
    }

    @Override
    public float getRarity() {
        return 0.4f;
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
        return 23000;
    }

    @Override
    public String getName() {
        return "Large fusion reactor Mk2";
    }

    @Override
    public int getWeight() {
        return 55;
    }

    @Override
    public void update() {

    }
}
