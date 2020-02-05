package com.mygdx.wargame.mech;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.google.common.collect.ImmutableMap;
import com.mygdx.wargame.battle.controller.SelectionController;
import com.mygdx.wargame.battle.unit.Direction;
import com.mygdx.wargame.battle.unit.State;
import com.mygdx.wargame.battle.unit.Team;
import com.mygdx.wargame.component.Component;
import com.mygdx.wargame.component.weapon.Weapon;
import com.mygdx.wargame.component.weapon.Status;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class AbstractMech extends Actor implements Mech {

    private Team team;
    private int movementPoints;
    private int initiative;
    private int step = 1;
    private int slow = 0;
    private State state = State.Idle;
    private Direction direction = Direction.Left;
    private float range = 15f;
    private int heatLevel;
    private int stability;

    private Map<BodyPart, Integer> bodyPartSizeLimitations = ImmutableMap.<BodyPart, Integer>builder()
            .put(BodyPart.LeftHand, 0)
            .put(BodyPart.RightHand, 0)
            .put(BodyPart.LeftLeg, 0)
            .put(BodyPart.RightLeg, 0)
            .put(BodyPart.Torso, 0)
            .put(BodyPart.Head, 0)
            .build();

    private Map<BodyPart, Integer> hp = ImmutableMap.<BodyPart, Integer>builder()
            .put(BodyPart.LeftHand, 0)
            .put(BodyPart.RightHand, 0)
            .put(BodyPart.LeftLeg, 0)
            .put(BodyPart.RightLeg, 0)
            .put(BodyPart.Torso, 0)
            .put(BodyPart.Head, 0)
            .build();

    private Map<BodyPart, Set<Component>> components = ImmutableMap.<BodyPart, Set<Component>>builder()
            .put(BodyPart.LeftHand, new HashSet<>())
            .put(BodyPart.RightHand, new HashSet<>())
            .put(BodyPart.LeftLeg, new HashSet<>())
            .put(BodyPart.RightLeg, new HashSet<>())
            .put(BodyPart.Torso, new HashSet<>())
            .put(BodyPart.Head, new HashSet<>())
            .build();

    protected TextureRegion textureRegion;

    @Override
    public int getHp(BodyPart bodyPart) {
        return hp.get(bodyPart);
    }

    @Override
    public boolean setHp(BodyPart bodyPart, int hp) {
        this.hp.put(bodyPart, hp);
        return this.hp.get(bodyPart) <= 0;
    }

    public void setState(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
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
    public Set<Component> getAllComponents() {
        return components.values().stream().flatMap(Collection::stream).collect(Collectors.toSet());
    }

    @Override
    public void draw(float x, float y, SpriteBatch spriteBatch, SelectionController selectionController, TextureRegion texture) {
        if (team == Team.enemy)
            spriteBatch.setColor(Color.WHITE);
        else
            spriteBatch.setColor(Color.YELLOW);

        if (slow == 0) {
            slow++;
            step++;
            if (step == 5) step = 1;
        } else {
            slow++;
            if (slow == 5)
                slow = 0;
        }

        texture.setRegion(step * 32, state.getCol() * 32, 32, 32);

        texture.flip(direction.isMirrored(), false);
        spriteBatch.draw(texture, x - 0.5f, y, 2, 2);
    }

    public void setMovementPoints(int movementPoints) {
        this.movementPoints = movementPoints;
    }

    public int getMovementPoints() {
        return this.movementPoints;
    }

    public void consumeMovementPoint(int amount) {
        this.movementPoints -= amount;
    }

    public int getInitiative() {
        return initiative;
    }

    public void setInitiative(int initiative) {
        this.initiative = initiative;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }

    public float getRange() {
        return range;
    }

    public int getHeatLevel() {
        return heatLevel;
    }

    @Override
    public void setCoordinates(float x, float y) {
        super.setPosition(x, y);
    }

    @Override
    public Set<Weapon> getSelectedWeapons() {
        return components.entrySet().stream()
                .filter(c -> Weapon.class.isAssignableFrom(c.getClass()))
                .map(c -> (Weapon)c)
                .filter(w -> w.getStatus().equals(Status.Selected))
                .collect(Collectors.toSet());
    }

    @Override
    public void addComponent(BodyPart bodyPart, Component component) {
        if(this.components.get(bodyPart).size() >= this.bodyPartSizeLimitations.get(bodyPart))
            return;
        this.components.get(bodyPart).add(component);
    }

    @Override
    public Set<Component> getComponents(BodyPart bodyPart) {
        return components.get(bodyPart);
    }

    @Override
    public int getStability() {
        return stability;
    }

    @Override
    public void setStability(int amount) {
        this.stability = amount;
    }
}
