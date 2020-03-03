package com.mygdx.wargame.mech;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.wargame.battle.unit.Direction;
import com.mygdx.wargame.battle.unit.State;
import com.mygdx.wargame.battle.unit.Team;
import com.mygdx.wargame.component.Component;
import com.mygdx.wargame.component.weapon.Weapon;

import java.util.Set;

public interface Mech extends Comparable<Mech> {
    public int getHp(BodyPart bodyPart);

    // true if bodypaert is destroyed
    public boolean setHp(BodyPart bodyPart, int hp);

    public void draw(float x, float y, SpriteBatch spriteBatch, TextureRegion texture);

    public float getX();

    public float getY();

    public String getName();

    Team getTeam();

    void setTeam(Team team);

    Set<Component> getAllComponents();

    int getHeatLevel();

    void setHeatLevel(int amount);

    int getRemainingMovementPoints();

    int getMaxMovementPoints();

    void resetMovementPoints(int amount);

    Set<Weapon> getSelectedWeapons();

    void addComponent(BodyPart bodyPart, Component component);

    Set<Component> getComponents(BodyPart bodyPart);

    int getStability();

    void setStability(int amount);

    int getStabilityResistance();

    int getLeftHandMaxHp();

    int getRightHandMaxHp();

    int getLeftLegMaxHp();

    int getRightLegMaxHp();

    int getTorsoMaxHp();

    int getHeadMaxHp();

    boolean moved();

    boolean attacked();

    void setAttacked(boolean attacked);

    void setMoved(boolean moved);

    boolean isActive();

    void setActive(boolean value);

    int getInitiative();

    void setState(State state);

    float getRange();

    int getMovementPoints();

    void setDirection(Direction direction);

    void consumeMovementPoint(int i);

    void setPosition(float x, float y);

    State getState();

    int getShieldValue();

    int getArmorValue();

    Direction getDirection();

    int getMaxHp(BodyPart bodyPart);
}
