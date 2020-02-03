package com.mygdx.wargame.component.armor;

public class CompositeMaterialArmor implements Armor {
    private static final int MAX_HP = 20;
    private int hp = 20;

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
        return 0.3f;
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
}
