package com.mygdx.wargame.mech;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.google.common.collect.ImmutableMap;
import com.mygdx.wargame.battle.controller.SelectionController;

import java.util.Map;

public class Scout extends AbstractMech {

    public static final int LEFT_HAND_HP = 10;
    public static final int RIGHT_HAND_HP = 10;
    public static final int LEFT_LEG_HP = 15;
    public static final int RIGHT_LEG_HP = 15;
    public static final int TORSO_HP = 30;
    public static final int HEAD_HP = 10;

    private SpriteBatch spriteBatch;
    private SelectionController selectionController;
    private String name;
    private int movementPoints;

    private Map<BodyPart, Integer> bodyPartSizeLimitations = ImmutableMap.<BodyPart, Integer>builder()
            .put(BodyPart.LeftHand, 3)
            .put(BodyPart.RightHand, 3)
            .put(BodyPart.LeftLeg, 5)
            .put(BodyPart.RightLeg, 5)
            .put(BodyPart.Torso, 3)
            .put(BodyPart.Head, 3)
            .build();

    private Map<BodyPart, Integer> hp = ImmutableMap.<BodyPart, Integer>builder()
            .put(BodyPart.LeftHand, LEFT_HAND_HP)
            .put(BodyPart.RightHand, RIGHT_HAND_HP)
            .put(BodyPart.LeftLeg, LEFT_LEG_HP)
            .put(BodyPart.RightLeg, RIGHT_LEG_HP)
            .put(BodyPart.Torso, TORSO_HP)
            .put(BodyPart.Head, HEAD_HP)
            .build();

    public Scout(String name, SpriteBatch spriteBatch, SelectionController selectionController, AssetManager assetManager) {
        this.spriteBatch = spriteBatch;
        this.selectionController = selectionController;
        this.name = name;

        setTouchable(Touchable.enabled);
        setSize(1, 1);
        this.textureRegion = new TextureRegion(assetManager.get("Maverick.png", Texture.class), 32, 0, 32, 32);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getRemainingMovementPoints() {
        return movementPoints;
    }

    @Override
    public int getMaxMovementPoints() {
        return 6;
    }

    @Override
    public void resetMovementPoints(int amount) {
        this.movementPoints = amount;
    }

    @Override
    public int getStabilityResistance() {
        return 0;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(getX(), getY(), spriteBatch, selectionController, textureRegion);
    }

    @Override
    public int getLeftHandMaxHp() {
        return LEFT_HAND_HP;
    }

    @Override
    public int getRightHandMaxHp() {
        return RIGHT_HAND_HP;
    }

    @Override
    public int getLeftLegMaxHp() {
        return LEFT_LEG_HP;
    }

    @Override
    public int getRightLegMaxHp() {
        return RIGHT_LEG_HP;
    }

    @Override
    public int getTorsoMaxHp() {
        return TORSO_HP;
    }

    @Override
    public int getHeadMaxHp() {
        return HEAD_HP;
    }
}
