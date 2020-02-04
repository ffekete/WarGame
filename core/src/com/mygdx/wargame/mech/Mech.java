package com.mygdx.wargame.mech;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.wargame.battle.controller.SelectionController;
import com.mygdx.wargame.battle.unit.Team;
import com.mygdx.wargame.component.Component;
import com.mygdx.wargame.component.weapon.Weapon;

import java.util.Set;

public interface Mech {
    public int getHp(BodyPart bodyPart);

    // true if bodypaert is destroyed
    public boolean setHp(BodyPart bodyPart, int hp);

    public void draw(float x, float y, SpriteBatch spriteBatch, SelectionController selectionController, TextureRegion texture);

    public float getX();

    public float getY();

    public String getName();

    Team getTeam();

    void setTeam(Team team);

    Set<Component> getAllComponents();

    int getHeatLevel();

    void setCoordinates(float x, float y);

    int getRemainingMovementPoints();

    int getMaxMovementPoints();

    void resetMovementPoints();

    Set<Weapon> getSelectedWeapons();

    void addComponent(BodyPart bodyPart, Component component);

    Set<Component> getComponents(BodyPart bodyPart);
}
