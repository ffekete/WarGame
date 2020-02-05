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
            .put(BodyPart.LeftHand, 10)
            .put(BodyPart.RightHand, 10)
            .put(BodyPart.LeftLeg, 15)
            .put(BodyPart.RightLeg, 15)
            .put(BodyPart.Torso, 30)
            .put(BodyPart.Head, 10)
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
    public void draw(Batch batch, float parentAlpha) {
        super.draw(getX(), getY(), spriteBatch, selectionController, textureRegion);
    }
}
