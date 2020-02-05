package com.mygdx.wargame.component.armor;

import com.mygdx.wargame.component.weapon.Status;

public class CompositeMaterialArmor implements Armor {
    private static final int MAX_HP = 20;
    private int hp = 20;
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
    public float getRarity() {
        return 0.3f;
    }

    @Override
    public void resetHitpoints() {
        this.hp = MAX_HP;
    }

    @Override
    public int getSlotSize() {
        return 1;
    }

    @Override
    public int getPowerConsumption() {
        return 0;
    }

    @Override
    public int getPrice() {
        return 1500;
    }

    @Override
    public String getName() {
        return "Composite material armor";
    }

    @Override
    public int getWeight() {
        return 3;
    }

    @Override
    public void update() {

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
