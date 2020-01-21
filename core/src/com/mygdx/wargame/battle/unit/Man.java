package com.mygdx.wargame.battle.unit;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.wargame.battle.controller.SelectionController;

public interface Man {
    public int getHp();

    public void setHp(int hp);

    void setUnit(Unit unit);

    Unit getUnit();

    public default void draw(float x, float y, ShapeRenderer shapeRenderer, SelectionController selectionController) {
        shapeRenderer.setColor(Color.YELLOW);
        shapeRenderer.circle(x, y, 5);
        if (selectionController.isSelected(this)) {
            shapeRenderer.setColor(Color.RED);
            shapeRenderer.circle(x, y, 6);
        }
    }

    public float getX();
    public float getY();

    public String getName();
}
