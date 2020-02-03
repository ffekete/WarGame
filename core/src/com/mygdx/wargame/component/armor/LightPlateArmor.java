package com.mygdx.wargame.component.armor;

public class LightPlateArmor implements Armor {
    private static final int MAX_HP = 8;
    private int hp = MAX_HP;

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
        return 1f;
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
        return 300;
    }

    @Override
    public String getName() {
        return "Light plate armor";
    }

    @Override
    public int getWeight() {
        return 2;
    }

    @Override
    public void update() {

    }
}