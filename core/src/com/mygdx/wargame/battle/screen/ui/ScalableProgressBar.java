package com.mygdx.wargame.battle.screen.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;

public class ScalableProgressBar extends ProgressBar {

    private float scale;

    public ScalableProgressBar(float min, float max, float stepSize, boolean vertical, ProgressBarStyle style, float scale, float x, float y) {
        super(min, max, stepSize, vertical, style);
        setPosition(x * (1 / scale), y * (1 / scale));
        this.scale = scale;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Matrix4 originalMatrix = batch.getProjectionMatrix().cpy(); // cpy() needed to properly set afterwards because calling set() seems to modify kept matrix, not replaces it
        batch.setProjectionMatrix(originalMatrix.cpy().scale(scale, scale, 1));
        super.draw(batch, parentAlpha);
        batch.setProjectionMatrix(originalMatrix); //revert projection
        //super.draw(batch, parentAlpha);
    }
}
