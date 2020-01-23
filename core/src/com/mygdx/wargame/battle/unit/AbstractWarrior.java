package com.mygdx.wargame.battle.unit;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.wargame.battle.controller.SelectionController;

public class AbstractWarrior extends Actor implements Man {

    public static final int RADIUS = 5;
    private Team team;
    private int movementPoints;

    @Override
    public int getHp() {
        return 1;
    }

    @Override
    public void setHp(int hp) {

    }

    @Override
    public Team getTeam() {
        return team;
    }

    @Override
    public void setTeam(Team team) {
        this.team = team;
    }

    @Override
    public void draw(float x, float y, ShapeRenderer shapeRenderer, SelectionController selectionController) {
        shapeRenderer.setColor(Color.YELLOW);
        shapeRenderer.circle(x + RADIUS, y + RADIUS, RADIUS);
        if (selectionController.isSelected(this)) {
            shapeRenderer.setColor(Color.RED);
            shapeRenderer.circle(x + RADIUS, y + RADIUS, RADIUS + 1);
        }

        shapeRenderer.rect(0, 0, 160, 120);
    }

    public void setMovementPoints(int movementPoints) {
        this.movementPoints = movementPoints;
    }

    public int getMovementPoints() {
        return this.movementPoints;
    }

    public void consumeMovementPoint(int amount) {
        this.movementPoints--;
    }

}
