package com.mygdx.wargame.component;

public interface Component {

    float getRarity();

    int getSlotSize();

    int getPowerConsumption();

    int getPrice();

    String getName();

    int getWeight();

    // called periodically for special abilities
    void update();

}
