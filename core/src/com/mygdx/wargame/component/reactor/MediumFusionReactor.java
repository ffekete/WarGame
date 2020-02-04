package com.mygdx.wargame.component.reactor;

import com.mygdx.wargame.component.weapon.Item;

@Item
public class MediumFusionReactor implements Reactor {

    @Override
    public int getPowerLevel() {
        return 75;
    }

    @Override
    public float getRarity() {
        return 1f;
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
        return 15000;
    }

    @Override
    public String getName() {
        return "Medium fusion reactor";
    }

    @Override
    public int getWeight() {
        return 45;
    }

    @Override
    public void update() {

    }
}
