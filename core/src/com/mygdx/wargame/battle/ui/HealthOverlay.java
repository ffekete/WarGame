package com.mygdx.wargame.battle.ui;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class HealthOverlay extends Image {

    private String headHealth;
    private String leftLegHealth;
    private String rightLegHealth;
    private String leftArmHealth;
    private String rightArmHealth;
    private String torsoHealth;
    private Label.LabelStyle labelStyle;
    private TextureRegion textureRegion;
    private int step = 0;
    private float delay = 0;

    public HealthOverlay(String headHealth, Label.LabelStyle labelStyle, TextureRegion textureRegion) {
        this.headHealth = headHealth;
        this.labelStyle = labelStyle;
        this.textureRegion = textureRegion;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        delay += Gdx.graphics.getDeltaTime();

        if (delay > 0.1f) {
            delay = 0;
            step = (step + 1) % 24;
        }

        textureRegion.setRegion(step * 48, 0, 48, 48);
        batch.setColor(Color.valueOf("FFFFFF99"));
        batch.draw(textureRegion, getX(), getY(), 2, 2);

        Matrix4 originalMatrix = batch.getProjectionMatrix().cpy(); // cpy() needed to properly set afterwards because calling set() seems to modify kept matrix, not replaces it
        batch.setProjectionMatrix(originalMatrix.cpy().scale(0.025f, 0.025f, 1));
        Color old = labelStyle.font.getColor();
        labelStyle.font.setColor(Color.valueOf("FFFFFF99"));
        labelStyle.font.draw(batch, headHealth, (getX() + 0.13f)* (1 / 0.025f), (getY() + 1.9f) * (1 / 0.025f));
        labelStyle.font.draw(batch, leftArmHealth, (getX() + 0.13f) * (1 / 0.025f), (getY() + 1.15f) * (1 / 0.025f));
        labelStyle.font.draw(batch, rightArmHealth, (getX() + 1.65f) * (1 / 0.025f), (getY() + 1.15f) * (1 / 0.025f));
        labelStyle.font.draw(batch, leftLegHealth, (getX() + 0.13f) * (1 / 0.025f), (getY() + 0.5f) * (1 / 0.025f));
        labelStyle.font.draw(batch, rightLegHealth, (getX() + 1.65f) * (1 / 0.025f), (getY() + 0.5f) * (1 / 0.025f));
        labelStyle.font.draw(batch, torsoHealth, (getX() + 1.65f) * (1 / 0.025f), (getY() + 1.9f) * (1 / 0.025f));
        labelStyle.font.setColor(old);
        batch.setProjectionMatrix(originalMatrix);
        batch.setColor(Color.WHITE);
    }

    public void setHeadHealth(String headHealth) {
        this.headHealth = headHealth;
    }

    public void setLeftLegHealth(String leftLegHealth) {
        this.leftLegHealth = leftLegHealth;
    }

    public void setRightLegHealth(String rightLegHealth) {
        this.rightLegHealth = rightLegHealth;
    }

    public void setLeftArmHealth(String leftArmHealth) {
        this.leftArmHealth = leftArmHealth;
    }

    public void setRightArmHealth(String rightArmHealth) {
        this.rightArmHealth = rightArmHealth;
    }

    public void setTorsoHealth(String torsoHealth) {
        this.torsoHealth = torsoHealth;
    }
}
