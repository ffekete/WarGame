package com.mygdx.wargame.battle.unit;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.wargame.battle.controller.SelectionController;

public interface Man {
    public int getHp();

    public void setHp(int hp);

    public void draw(float x, float y, ShapeRenderer shapeRenderer, SelectionController selectionController);

    public float getX();
    public float getY();

    public String getName();

    Team getTeam();

    void setTeam(Team team);
}
