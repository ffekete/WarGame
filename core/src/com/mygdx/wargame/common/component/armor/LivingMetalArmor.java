package com.mygdx.wargame.common.component.armor;

import com.mygdx.wargame.common.component.weapon.Status;

public class LivingMetalArmor implements Armor {

    private static final int MAX_HP = 10;
    private int hp = 10;
    private Status status;

    @Override
    public int getHitPoint() {
        return hp;
    }

    @Override
    public int getMaxHitpoint() {
        return MAX_HP;
    }

    @Override
    public int reduceHitPoint(int amount) {
        hp -= amount;
        return hp;
    }

    @Override
    public void resetHitpoints() {
        this.hp = MAX_HP;
    }

    @Override
    public float getRarity() {
        return 0.05f;
    }

    @Override
    public int getSlotSize() {
        return 2;
    }

    @Override
    public int getPowerConsumption() {
        return 5;
    }

    @Override
    public int getPrice() {
        return 4000;
    }

    @Override
    public String getName() {
        return "Living metal armor";
    }

    @Override
    public int getWeight() {
        return 8;
    }

    @Override
    public void update() {
        if (this.hp < MAX_HP)
            this.hp++;
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
