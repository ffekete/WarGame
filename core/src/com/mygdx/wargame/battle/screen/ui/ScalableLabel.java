package com.mygdx.wargame.battle.screen.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class ScalableLabel extends Label {

    private float scale;

    public ScalableLabel(CharSequence text, LabelStyle style, float scale) {
        super(text, style);
        this.scale = scale;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Matrix4 originalMatrix = batch.getProjectionMatrix().cpy(); // cpy() needed to properly set afterwards because calling set() seems to modify kept matrix, not replaces it
        batch.setProjectionMatrix(originalMatrix.cpy().scale(scale, scale, 1));
        Color old = getStyle().font.getColor();
        getStyle().font.setColor(getColor());
        getStyle().font.draw(batch, getText(), getX() * (1 / scale), getY() * (1 / scale));
        getStyle().font.setColor(old);
        batch.setProjectionMatrix(originalMatrix); //revert projection
    }
}
