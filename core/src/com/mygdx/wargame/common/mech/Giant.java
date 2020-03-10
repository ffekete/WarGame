package com.mygdx.wargame.common.mech;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.google.common.collect.ImmutableMap;
import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.battle.screen.TurnProcessingFacadeStore;
import com.mygdx.wargame.common.component.Component;
import com.mygdx.wargame.common.component.weapon.Status;
import com.mygdx.wargame.common.component.weapon.Weapon;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Giant extends AbstractMech {

    public static final int LEFT_HAND_HP = 30;
    public static final int RIGHT_HAND_HP = 30;
    public static final int LEFT_LEG_HP = 40;
    public static final int RIGHT_LEG_HP = 40;
    public static final int TORSO_HP = 80;
    public static final int HEAD_HP = 30;

    private SpriteBatch spriteBatch;
    private String name;
    private int movementPoints;

    private Map<BodyPart, Integer> bodyPartSizeLimitations = ImmutableMap.<BodyPart, Integer>builder()
            .put(BodyPart.LeftArm, 3)
            .put(BodyPart.RightArm, 3)
            .put(BodyPart.LeftLeg, 5)
            .put(BodyPart.RightLeg, 5)
            .put(BodyPart.Torso, 3)
            .put(BodyPart.Head, 3)
            .build();

    private Map<BodyPart, Integer> hp = new HashMap<>();

    private Map<BodyPart, Set<Component>> components = ImmutableMap.<BodyPart, Set<Component>>builder()
            .put(BodyPart.LeftArm, new HashSet<>())
            .put(BodyPart.RightArm, new HashSet<>())
            .put(BodyPart.LeftLeg, new HashSet<>())
            .put(BodyPart.RightLeg, new HashSet<>())
            .put(BodyPart.Torso, new HashSet<>())
            .put(BodyPart.Head, new HashSet<>())
            .build();

    public Giant(String name, SpriteBatch spriteBatch, AssetManager assetManager, BattleMap battleMap, TurnProcessingFacadeStore turnProcessingFacadeStore) {
        super(10, assetManager, battleMap, turnProcessingFacadeStore);
        this.spriteBatch = spriteBatch;
        this.name = name;

        setTouchable(Touchable.enabled);
        setSize(1, 1);
        this.mechTextureRegion = new TextureRegion(assetManager.get("Scout.png", Texture.class), 0, 0, 48, 48);

        hp.put(BodyPart.LeftArm, getLeftHandMaxHp());
        hp.put(BodyPart.RightArm, getRightHandMaxHp());
        hp.put(BodyPart.LeftLeg, getLeftLegMaxHp());
        hp.put(BodyPart.RightLeg, getRightLegMaxHp());
        hp.put(BodyPart.Torso, getTorsoMaxHp());
        hp.put(BodyPart.Head, getHeadMaxHp());

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

    @Override
    public int getMaxHp(BodyPart bodyPart) {
        if (bodyPart == BodyPart.Head)
            return HEAD_HP;

        if (bodyPart == BodyPart.Torso)
            return TORSO_HP;

        if (bodyPart == BodyPart.LeftLeg)
            return LEFT_LEG_HP;

        if (bodyPart == BodyPart.LeftArm)
            return LEFT_HAND_HP;

        if (bodyPart == BodyPart.RightLeg)
            return RIGHT_LEG_HP;

        if (bodyPart == BodyPart.RightArm)
            return RIGHT_HAND_HP;

        return 999; // never, pls
    }
}
