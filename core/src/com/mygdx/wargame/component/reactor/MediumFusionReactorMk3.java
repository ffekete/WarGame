package com.mygdx.wargame.component.reactor;

import com.mygdx.wargame.component.weapon.Item;

@Item
public class MediumFusionReactorMk3 implements Reactor {

    @Override
    public int getPowerLevel() {
        return 80;
    }

    @Override
    public float getRarity() {
        return 0.1f;
    }

    @Override
    public int getSlotSize() {
        return 12;
    }

    @Override
    public int getPowerConsumption() {
        return 0;
    }

    @Override
    public int getPrice() {
        return 22000;
    }

    @Override
    public String getName() {
        return "Medium fusion reactor Mk3";
    }

    @Override
    public int getWeight() {
        return 40;
    }

    @Override
    public void update() {

    }
}
