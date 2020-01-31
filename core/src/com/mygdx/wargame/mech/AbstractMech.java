package com.mygdx.wargame.mech;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.wargame.battle.controller.SelectionController;
import com.mygdx.wargame.battle.unit.Direction;
import com.mygdx.wargame.battle.unit.State;
import com.mygdx.wargame.battle.unit.Team;

public class AbstractMech extends Actor implements Man {

    private Team team;
    private int movementPoints;
    private int initiative;
    private int step = 1;
    private int slow = 0;
    private State state = State.Idle;
    private Direction direction = Direction.Left;
    private float range = 15f;

    protected TextureRegion textureRegion;

    @Override
    public int getHp() {
        return 1;
    }

    @Override
    public void setHp(int hp) {

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
        this.movementPoints-= amount;
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
}
