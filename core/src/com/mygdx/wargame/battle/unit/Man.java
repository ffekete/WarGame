package com.mygdx.wargame.battle.unit;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.wargame.battle.controller.SelectionController;

public interface Man {
    public int getHp();

    public void setHp(int hp);

    public void draw(float x, float y, SpriteBatch spriteBatch, SelectionController selectionController, TextureRegion texture);

    public float getX();
    public float getY();

    public String getName();

    Team getTeam();

    void setTeam(Team team);
}
