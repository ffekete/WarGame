package com.mygdx.wargame.common.mech;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.wargame.battle.screen.IsometricSprite;
import com.mygdx.wargame.battle.unit.Direction;
import com.mygdx.wargame.battle.unit.State;
import com.mygdx.wargame.battle.unit.Team;
import com.mygdx.wargame.common.component.armor.Armor;
import com.mygdx.wargame.common.component.shield.Shield;
import com.mygdx.wargame.common.component.weapon.Status;

public abstract class
AbstractMech extends Actor implements Mech {

    private Team team;
    private int initiative;
    private State state = State.Idle;
    private Direction direction = Direction.Left;
    private float range = 15f;
    private int heatLevel;
    private int stability;
    private boolean attacked;
    private boolean moved;
    private boolean active;

    protected IsometricSprite isometricSprite;

    public AbstractMech(int initiative, IsometricSprite isometricSprite) {
        this.initiative = initiative;
        this.isometricSprite = isometricSprite;
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
    public void draw(Batch spriteBatch, float parentAlpha) {
        isometricSprite.draw(spriteBatch, parentAlpha);
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
    public void setHeatLevel(int amount) {
        this.heatLevel = amount;
    }

    @Override
    public int getStability() {
        return stability;
    }

    @Override
    public void setStability(int amount) {
        this.stability = amount;
    }

    @Override
    public boolean moved() {
        return moved;
    }

    @Override
    public boolean attacked() {
        return attacked;
    }

    @Override
    public void setAttacked(boolean attacked) {
        this.attacked = attacked;
    }

    @Override
    public void setMoved(boolean moved) {
        this.moved = moved;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public void setActive(boolean value) {
        this.active = value;
    }

    @Override
    public int compareTo(Mech m) {

        if (m.getInitiative() == this.getInitiative()) {
            return 1;
        }
        return Integer.compare(this.getInitiative(), m.getInitiative());
    }

    @Override
    public int getShieldValue() {
        return getAllComponents().stream()
                .filter(c -> c.getStatus() != Status.Destroyed)
                .filter(c -> Shield.class.isAssignableFrom(c.getClass()))
                .map(s -> ((Shield) s).getShieldValue())
                .reduce((a, b) -> a + b)
                .orElse(0);
    }

    @Override
    public int getArmorValue() {
        return getAllComponents().stream()
                .filter(c -> c.getStatus() != Status.Destroyed)
                .filter(c -> Armor.class.isAssignableFrom(c.getClass()))
                .map(a -> ((Armor) a).getHitPoint())
                .reduce((a, b) -> a + b)
                .orElse(0);
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        isometricSprite.setPosition(x, y);
    }
}
