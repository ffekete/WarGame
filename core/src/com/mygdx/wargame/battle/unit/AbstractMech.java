package com.mygdx.wargame.battle.unit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.wargame.battle.controller.SelectionController;

public class AbstractMech extends Actor implements Man {

    public static final int RADIUS = 2;
    private Team team;
    private int movementPoints;
    private int initiative;

    public TextureRegion textureRegion;

    public AbstractMech() {
        this.textureRegion =  new TextureRegion(new Texture(Gdx.files.internal("Maverick.png")));
    }

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
    public void draw(float x, float y, SpriteBatch spriteBatch, SelectionController selectionController, TextureRegion texture) {
        spriteBatch.setColor(Color.WHITE);
        spriteBatch.draw(texture, x, y, 2, 2);
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

    public int getInitiative() {
        return initiative;
    }

    public void setInitiative(int initiative) {
        this.initiative = initiative;
    }
}
