package com.mygdx.wargame.component.shield;

import com.mygdx.wargame.component.weapon.Item;
import com.mygdx.wargame.component.weapon.Status;

@Item
public class MediumShieldModule implements Shield {

    private static final int MAX_VALUE = 20;
    private int value = MAX_VALUE;
    private Status status;

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
        return 7;
    }

    @Override
    public int getPrice() {
        return 900;
    }

    @Override
    public String getName() {
        return "Medium shield module";
    }

    @Override
    public int getWeight() {
        return 7;
    }

    @Override
    public void update() {
        value += 1;
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
