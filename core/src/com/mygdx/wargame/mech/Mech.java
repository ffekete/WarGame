package com.mygdx.wargame.mech;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.wargame.battle.controller.SelectionController;
import com.mygdx.wargame.battle.unit.Team;
import com.mygdx.wargame.component.Component;

import java.util.Set;

public interface Mech {
    public int getHp();

    public void setHp(int hp);

    public void draw(float x, float y, SpriteBatch spriteBatch, SelectionController selectionController, TextureRegion texture);

    public float getX();

    public float getY();

    public String getName();

    Team getTeam();

    void setTeam(Team team);

    Set<Component> getAllComponents();

    int getHeatLevel();

    void setCoordinates(float x, float y);
}
