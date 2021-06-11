package com.mygdx.wargame.common.mech;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mygdx.wargame.battle.screen.AssetManagerLoaderV2;
import com.mygdx.wargame.battle.screen.IsometricAnimatedSprite;
import com.mygdx.wargame.common.component.Component;
import com.mygdx.wargame.common.component.weapon.WeaponType;
import com.mygdx.wargame.common.component.weapon.ballistic.LargeCannonMk3;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class Tank extends AbstractMech {

    public static final int LEFT_HAND_HP = 10;
    public static final int RIGHT_HAND_HP = 10;
    public static final int LEFT_LEG_HP = 15;
    public static final int RIGHT_LEG_HP = 15;
    public static final int TORSO_HP = 30;
    public static final int HEAD_HP = 10;

    private String name;
    private int movementPoints;

    public Tank(String name, AssetManagerLoaderV2 assetManagerLoader) {
        super(20, new IsometricAnimatedSprite(assetManagerLoader.getAssetManager().get("mechs/Tank.png", Texture.class), 60),
                new IsometricAnimatedSprite(assetManagerLoader.getAssetManager().get("info/EnemyMarker.png", Texture.class), 60));
        this.name = name;

        setTouchable(Touchable.enabled);
        setSize(1, 1);

        bodyPartSizeLimitations = ImmutableMap.<BodyPart, Integer>builder()
                .put(BodyPart.LeftArm, 0)
                .put(BodyPart.RightArm, 0)
                .put(BodyPart.LeftLeg, 5)
                .put(BodyPart.RightLeg, 5)
                .put(BodyPart.Torso, 3)
                .put(BodyPart.Head, 3)
                .build();

        components = ImmutableMap.<BodyPart, Set<Component>>builder()
                .put(BodyPart.LeftArm, new HashSet<>())
                .put(BodyPart.RightArm, new HashSet<>())
                .put(BodyPart.LeftLeg, new HashSet<>())
                .put(BodyPart.RightLeg, new HashSet<>())
                .put(BodyPart.Torso, new HashSet<>())
                .put(BodyPart.Head, new HashSet<>())
                .build();

        hp.put(BodyPart.LeftArm, getLeftHandMaxHp());
        hp.put(BodyPart.RightArm, getRightHandMaxHp());
        hp.put(BodyPart.LeftLeg, getLeftLegMaxHp());
        hp.put(BodyPart.RightLeg, getRightLegMaxHp());
        hp.put(BodyPart.Torso, getTorsoMaxHp());
        hp.put(BodyPart.Head, getHeadMaxHp());

        weaponSlots = ImmutableMap.<BodyPart, List<WeaponSlot>>builder()
                .put(BodyPart.Torso, ImmutableList.of(
                        new WeaponSlot(ImmutableList.of(WeaponType.Ballistic, WeaponType.Missile, WeaponType.Flamer, WeaponType.Ion, WeaponType.Plasma)),
                        new WeaponSlot(ImmutableList.of(WeaponType.Ballistic, WeaponType.Missile, WeaponType.Flamer, WeaponType.Ion, WeaponType.Plasma))))
                .build();

        bodyDefinition = ImmutableMap.<BodyPart, Optional<String>>builder()
                .put(BodyPart.LeftLeg, Optional.of("Left track"))
                .put(BodyPart.RightLeg, Optional.of("Right track"))
                .put(BodyPart.Head, Optional.of("Cockpit"))
                .put(BodyPart.Torso, Optional.of("Torso"))
                .put(BodyPart.LeftArm, Optional.empty())
                .put(BodyPart.RightArm, Optional.empty())
                .build();

        addWeapon(BodyPart.Torso, new LargeCannonMk3());
        addWeapon(BodyPart.Torso, new LargeCannonMk3());
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
        return 1;
    }

    @Override
    public void resetMovementPoints(int amount) {
        this.movementPoints = amount;
    }

    @Override
    public int getStabilityResistance() {
        return 100;
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
    public void consumeMovementPoint(int amount) {
        this.movementPoints -= amount;
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

    @Override
    public int getMovementPoints() {
        return this.movementPoints;
    }

    @Override
    public int getMeleeDamage() {
        return 0;
    }

    @Override
    public Role getRole() {
        return Role.FrontLine;
    }
}
