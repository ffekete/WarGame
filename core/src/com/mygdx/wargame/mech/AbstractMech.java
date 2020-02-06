package com.mygdx.wargame.mech;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.wargame.battle.unit.Direction;
import com.mygdx.wargame.battle.unit.State;
import com.mygdx.wargame.battle.unit.Team;
import com.mygdx.wargame.rules.facade.TurnProcessingFacade;

public abstract class
AbstractMech extends Actor implements Mech {

    private Team team;
    private int initiative;
    private int step = 1;
    private int slow = 0;
    private State state = State.Idle;
    private Direction direction = Direction.Left;
    private float range = 15f;
    private int heatLevel;
    private int stability;
    private boolean attacked;
    private boolean moved;
    private boolean active;

    protected TextureRegion textureRegion;

    public AbstractMech(int initiative) {
        this.initiative = initiative;
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
    public void draw(float x, float y, SpriteBatch spriteBatch, TextureRegion texture) {
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
    public void setCoordinates(float x, float y) {
        super.setPosition(x, y);
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
}
