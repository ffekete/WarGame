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

    private String headArmor;
    private String torsoArmor;
    private String leftLegArmor;
    private String rightLegArmor;
    private String leftArmArmor;
    private String rightArmArmor;

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
        batch.draw(textureRegion, getX(), getY(), 2.8f, 2);

        Matrix4 originalMatrix = batch.getProjectionMatrix().cpy(); // cpy() needed to properly set afterwards because calling set() seems to modify kept matrix, not replaces it
        float scaleX = 0.02f;
        float scaleY = 0.02f;

        batch.setProjectionMatrix(originalMatrix.cpy().scale(scaleX, scaleY, 1));
        Color old = labelStyle.font.getColor();

        labelStyle.font.setColor(Color.valueOf("00FF0099"));
        labelStyle.font.draw(batch, headHealth, (getX() + 0.37f)* (1 / scaleX), (getY() + 1.9f) * (1 / scaleY));
        labelStyle.font.setColor(Color.valueOf("C0C0C099"));
        labelStyle.font.draw(batch, headArmor, (getX() + 0.17f)* (1 / scaleX), (getY() + 1.9f) * (1 / scaleY));

        labelStyle.font.setColor(Color.valueOf("00FF0099"));
        labelStyle.font.draw(batch, leftArmHealth, (getX() + 0.37f) * (1 / scaleX), (getY() + 1.15f) * (1 / scaleY));
        labelStyle.font.setColor(Color.valueOf("C0C0C099"));
        labelStyle.font.draw(batch, leftArmArmor, (getX() + 0.17f)* (1 / scaleX), (getY() + 1.15f) * (1 / scaleY));

        labelStyle.font.setColor(Color.valueOf("00FF0099"));
        labelStyle.font.draw(batch, rightArmHealth, (getX() + 2.45f) * (1 / scaleX), (getY() + 1.15f) * (1 / scaleY));
        labelStyle.font.setColor(Color.valueOf("C0C0C099"));
        labelStyle.font.draw(batch, rightArmArmor, (getX() + 2.15f)* (1 / scaleX), (getY() + 1.15f) * (1 / scaleY));

        labelStyle.font.setColor(Color.valueOf("00FF0099"));
        labelStyle.font.draw(batch, leftLegHealth, (getX() + 0.37f) * (1 / scaleX), (getY() + 0.5f) * (1 / scaleY));
        labelStyle.font.setColor(Color.valueOf("C0C0C099"));
        labelStyle.font.draw(batch, leftLegArmor, (getX() + 0.17f)* (1 / scaleX), (getY() + 0.5f) * (1 / scaleY));

        labelStyle.font.setColor(Color.valueOf("00FF0099"));
        labelStyle.font.draw(batch, rightLegHealth, (getX() + 2.45f) * (1 / scaleX), (getY() + 0.5f) * (1 / scaleY));
        labelStyle.font.setColor(Color.valueOf("C0C0C099"));
        labelStyle.font.draw(batch, rightLegArmor, (getX() + 2.15f)* (1 / scaleX), (getY() + 0.5f) * (1 / scaleY));

        labelStyle.font.setColor(Color.valueOf("00FF0099"));
        labelStyle.font.draw(batch, torsoHealth, (getX() + 2.45f) * (1 / scaleX), (getY() + 1.9f) * (1 / scaleY));
        labelStyle.font.setColor(Color.valueOf("C0C0C099"));
        labelStyle.font.draw(batch, torsoArmor, (getX() + 2.15f)* (1 / scaleX), (getY() + 1.9f) * (1 / scaleY));

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

    public void setHeadArmor(String headArmor) {
        this.headArmor = headArmor;
    }

    public void setTorsoArmor(String torsoArmor) {
        this.torsoArmor = torsoArmor;
    }

    public void setLeftLegArmor(String leftLegArmor) {
        this.leftLegArmor = leftLegArmor;
    }

    public void setRightLegArmor(String rightLegArmor) {
        this.rightLegArmor = rightLegArmor;
    }

    public void setLeftArmArmor(String leftArmArmor) {
        this.leftArmArmor = leftArmArmor;
    }

    public void setRightArmArmor(String rightArmArmor) {
        this.rightArmArmor = rightArmArmor;
    }
}
