package com.mygdx.wargame.mech;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.google.common.collect.ImmutableMap;
import com.mygdx.wargame.component.Component;
import com.mygdx.wargame.component.weapon.Status;
import com.mygdx.wargame.component.weapon.Weapon;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Scout extends AbstractMech {

    public static final int LEFT_HAND_HP = 10;
    public static final int RIGHT_HAND_HP = 10;
    public static final int LEFT_LEG_HP = 15;
    public static final int RIGHT_LEG_HP = 15;
    public static final int TORSO_HP = 30;
    public static final int HEAD_HP = 10;

    private SpriteBatch spriteBatch;
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

    private Map<BodyPart, Integer> hp = new HashMap<>();

    private Map<BodyPart, Set<Component>> components = ImmutableMap.<BodyPart, Set<Component>>builder()
            .put(BodyPart.LeftHand, new HashSet<>())
            .put(BodyPart.RightHand, new HashSet<>())
            .put(BodyPart.LeftLeg, new HashSet<>())
            .put(BodyPart.RightLeg, new HashSet<>())
            .put(BodyPart.Torso, new HashSet<>())
            .put(BodyPart.Head, new HashSet<>())
            .build();

    public Scout(String name, SpriteBatch spriteBatch, AssetManager assetManager) {
        super(10, assetManager);
        this.spriteBatch = spriteBatch;
        this.name = name;

        setTouchable(Touchable.enabled);
        setSize(1, 1);
        this.mechTextureRegion = new TextureRegion(assetManager.get("Mech01.png", Texture.class), 0, 0, 48, 48);

        hp.put(BodyPart.LeftHand, getLeftHandMaxHp());
        hp.put(BodyPart.RightHand, getRightHandMaxHp());
        hp.put(BodyPart.LeftLeg, getLeftLegMaxHp());
        hp.put(BodyPart.RightLeg, getRightLegMaxHp());
        hp.put(BodyPart.Torso, getTorsoMaxHp());
        hp.put(BodyPart.Head,getHeadMaxHp());

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
        super.draw(getX(), getY(), spriteBatch, mechTextureRegion);
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

    @Override
    public int getHp(BodyPart bodyPart) {
        return hp.get(bodyPart);
    }

    @Override
    public boolean setHp(BodyPart bodyPart, int hp) {
        this.hp.put(bodyPart, hp);
        return this.hp.get(bodyPart) <= 0;
    }

    @Override
    public void addComponent(BodyPart bodyPart, Component component) {
        if (this.components.get(bodyPart).size() >= this.bodyPartSizeLimitations.get(bodyPart))
            return;
        this.components.get(bodyPart).add(component);
    }

    @Override
    public Set<Weapon> getSelectedWeapons() {
        return components.entrySet().stream()
                .flatMap(bp -> bp.getValue().stream())
                .filter(c -> Weapon.class.isAssignableFrom(c.getClass()))
                .map(c -> (Weapon) c)
                .filter(w -> w.getStatus().equals(Status.Selected))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Component> getAllComponents() {
        return components.values().stream().flatMap(Collection::stream).collect(Collectors.toSet());
    }

    @Override
    public Set<Component> getComponents(BodyPart bodyPart) {
        return components.get(bodyPart);
    }


    @Override
    public void consumeMovementPoint(int amount) {
        this.movementPoints -= amount;
    }

    @Override
    public int getMovementPoints() {
        return this.movementPoints;
    }
}
