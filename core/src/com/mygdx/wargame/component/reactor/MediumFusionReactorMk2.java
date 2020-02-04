package com.mygdx.wargame.component.reactor;

import com.mygdx.wargame.component.weapon.Item;

@Item
public class MediumFusionReactorMk2 implements Reactor {

    @Override
    public int getPowerLevel() {
        return 80;
    }

    @Override
    public float getRarity() {
        return 0.4f;
    }

    @Override
    public int getSlotSize() {
        return 15;
    }

    @Override
    public int getPowerConsumption() {
        return 0;
    }

    @Override
    public int getPrice() {
        return 18000;
    }

    @Override
    public String getName() {
        return "Medium fusion reactor Mk2";
    }

    @Override
    public int getWeight() {
        return 40;
    }

    @Override
    public void update() {

    }
}
