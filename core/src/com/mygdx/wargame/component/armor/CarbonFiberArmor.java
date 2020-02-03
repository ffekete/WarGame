package com.mygdx.wargame.component.armor;

public class CarbonFiberArmor implements Armor {
    private static final int MAX_HP = 25;
    private int hp = 25;

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
        return hp - amount;
    }

    @Override
    public float getRarity() {
        return 0.01f;
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
        return 2500;
    }

    @Override
    public String getName() {
        return "Carbon fibre armor";
    }

    @Override
    public int getWeight() {
        return 2;
    }

    @Override
    public void update() {

    }
}
