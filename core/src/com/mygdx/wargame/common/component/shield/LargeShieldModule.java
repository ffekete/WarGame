package com.mygdx.wargame.common.component.shield;

import com.mygdx.wargame.common.component.weapon.Item;
import com.mygdx.wargame.common.component.weapon.Status;

@Item
public class LargeShieldModule implements Shield {

    private static final int MAX_VALUE = 30;
    private int value = MAX_VALUE;
    private Status status = Status.Selected;

    @Override
    public int getShieldValue() {
        return value;
    }

    @Override
    public void reduceShieldValue(int amount) {
        this.value -= amount;
        if (value < 0)
            value = 0;
    }

    @Override
    public void resetShieldValue() {
        this.value = MAX_VALUE;
    }

    @Override
    public float getRarity() {
        return 1f;
    }

    @Override
    public int getSlotSize() {
        return 3;
    }

    @Override
    public int getPowerConsumption() {
        return 10;
    }

    @Override
    public int getPrice() {
        return 1200;
    }

    @Override
    public String getName() {
        return "Large shield module";
    }

    @Override
    public int getWeight() {
        return 10;
    }

    @Override
    public void update() {
        value += 2;
        if (value > MAX_VALUE)
            value = MAX_VALUE;
    }

    @Override
    public Status getStatus() {
        return status;
    }

    @Override
    public void setStatus(Status status) {
        this.status = status;
    }
}
