package com.mygdx.wargame.common.component;

import com.mygdx.wargame.common.component.weapon.Status;

public interface Component {

    float getRarity();

    int getSlotSize();

    int getPowerConsumption();

    int getPrice();

    String getName();

    int getWeight();

    // called periodically for special abilities
    void update();

    Status getStatus();

    void setStatus(Status status);
}
