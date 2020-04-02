package com.mygdx.wargame.common.mech;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.battle.map.BattleMapStore;
import com.mygdx.wargame.battle.screen.IsometricAnimatedSprite;
import com.mygdx.wargame.battle.unit.Direction;
import com.mygdx.wargame.battle.unit.State;
import com.mygdx.wargame.battle.unit.Team;
import com.mygdx.wargame.common.component.Component;
import com.mygdx.wargame.common.component.armor.Armor;
import com.mygdx.wargame.common.component.shield.Shield;
import com.mygdx.wargame.common.component.weapon.Status;
import com.mygdx.wargame.common.component.weapon.Weapon;
import com.mygdx.wargame.config.Config;

import java.util.*;
import java.util.stream.Collectors;

import static com.mygdx.wargame.common.component.weapon.Status.Destroyed;
import static com.mygdx.wargame.common.component.weapon.Status.Selected;

public abstract class
AbstractMech extends Actor implements Mech {

    private Team team;
    private int initiative;
    private State state = State.Idle;
    private Direction direction = Direction.Left;
    private float range = 15f;
    private int heatLevel;
    private int stability;
    private boolean attacked;
    private boolean moved;
    private boolean active;
    private boolean rangedAttack;
    protected Map<BodyPart, List<WeaponSlot>> weaponSlots;

    protected IsometricAnimatedSprite isometricSprite;
    protected IsometricAnimatedSprite enemyMarker;

    protected Map<BodyPart, Optional<String>> bodyDefinition;

    protected Map<BodyPart, Set<Component>> components;

    protected Map<BodyPart, Integer> bodyPartSizeLimitations;

    protected Map<BodyPart, Integer> hp = new HashMap<>();

    public AbstractMech(int initiative, IsometricAnimatedSprite isometricSprite, IsometricAnimatedSprite enemyMarker) {
        this.initiative = initiative;
        this.isometricSprite = isometricSprite;
        stability = 100;
        heatLevel = 0;
        this.enemyMarker = enemyMarker;
    }

    @Override
    public int getHp(BodyPart bodyPart) {
        if (bodyDefinition.get(bodyPart).isPresent())
            return hp.get(bodyPart);
        else
            return 0;
    }

    @Override
    public void setHp(BodyPart bodyPart, int hp) {
        if (bodyDefinition.get(bodyPart).isPresent()) {
            this.hp.put(bodyPart, hp);
        }
    }

    @Override
    public Set<Component> getAllComponents() {
        return components.values().stream().filter(Objects::nonNull).flatMap(Collection::stream).collect(Collectors.toSet());
    }

    @Override
    public Set<Component> getComponents(BodyPart bodyPart) {
        return bodyDefinition.get(bodyPart).isPresent() ? components.get(bodyPart) : new HashSet<>();
    }

    @Override
    public void addComponent(BodyPart bodyPart, Component component) {

        if (!bodyDefinition.get(bodyPart).isPresent())
            return;

        if (this.components.get(bodyPart).size() >= this.bodyPartSizeLimitations.get(bodyPart))
            return;
        this.components.get(bodyPart).add(component);
    }

    public void setState(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }

    @Override
    public Team getTeam() {
        return team;
    }

    @Override
    public void setTeam(Team team) {
        this.team = team;
    }

    @Override
    public void draw(Batch spriteBatch, float parentAlpha) {
        Color originalColor = spriteBatch.getColor();
        if (Config.showTeamMarkers) {
            enemyMarker.setPosition(getX(), getY());

            if (team == Team.enemy) {
                enemyMarker.setColor(Color.valueOf("FF000077"));
            } else {
                enemyMarker.setColor(Color.valueOf("00FF0077"));
            }

            enemyMarker.draw(spriteBatch, parentAlpha);
        }

        isometricSprite.setColor(getColor());
        int height = getX() >= 0 && getY() >= 0 && getX() < BattleMap.WIDTH && getY() < BattleMap.HEIGHT ? BattleMapStore.battleMap.getNodeGraph().getNodeWeb()[(int)getX()][(int)getY()].getTile().getTileWorldHeight() : 0;

        if(this.canFly())
            height += 16;

        isometricSprite.draw(spriteBatch, parentAlpha, height);

        spriteBatch.setColor(Color.WHITE);
    }

    public int getInitiative() {
        return initiative;
    }

    public void setInitiative(int initiative) {
        this.initiative = initiative;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }

    public float getRange() {
        return range;
    }

    public int getHeatLevel() {
        return heatLevel;
    }

    @Override
    public void setHeatLevel(int amount) {
        this.heatLevel = amount;
    }

    @Override
    public int getStability() {
        return stability;
    }

    @Override
    public void setStability(int amount) {
        this.stability = amount;
    }

    @Override
    public boolean moved() {
        return moved;
    }

    @Override
    public boolean attacked() {
        return attacked;
    }

    @Override
    public void setAttacked(boolean attacked) {
        this.attacked = attacked;
    }

    @Override
    public void setMoved(boolean moved) {
        this.moved = moved;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public void setActive(boolean value) {
        this.active = value;
    }

    @Override
    public int compareTo(Mech m) {

        if (m.getInitiative() == this.getInitiative()) {
            return 1;
        }
        return Integer.compare(this.getInitiative(), m.getInitiative());
    }

    @Override
    public int getShieldValue() {
        return getAllComponents().stream()
                .filter(c -> c.getStatus() != Status.Destroyed)
                .filter(c -> Shield.class.isAssignableFrom(c.getClass()))
                .map(s -> ((Shield) s).getShieldValue())
                .reduce((a, b) -> a + b)
                .orElse(0);
    }

    @Override
    public int getArmorValue() {
        return getAllComponents().stream()
                .filter(c -> c.getStatus() != Status.Destroyed)
                .filter(c -> Armor.class.isAssignableFrom(c.getClass()))
                .map(a -> ((Armor) a).getHitPoint())
                .reduce((a, b) -> a + b)
                .orElse(0);
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        isometricSprite.setPosition(x, y);
    }

    @Override
    public int getAmmoCount() {
        return getAllWeapons().stream().map(w -> ((Weapon) w).getAmmo()).reduce((a, b) -> {
            return Optional.of(a.orElse(0) + b.orElse(0));
        }).orElse(Optional.of(0)).get();
    }

    @Override
    public boolean isRangedAttack() {
        return rangedAttack;
    }

    @Override
    public void setRangedAttack(boolean rangedAttack) {
        this.rangedAttack = rangedAttack;
    }

    @Override
    public Set<Weapon> getSelectedWeapons() {
        return weaponSlots.values().stream()
                .flatMap(Collection::parallelStream)
                .filter(weaponSlot -> weaponSlot.getWeapon().getStatus() == Selected)
                .map(WeaponSlot::getWeapon)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Weapon> getAllWeapons() {
        return weaponSlots.values().stream().flatMap(Collection::parallelStream)
                .filter(weaponSlot -> weaponSlot.getWeapon().getStatus() != Destroyed)
                .map(WeaponSlot::getWeapon)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Weapon> getAllWeapons(BodyPart bodyPart) {
        if (!weaponSlots.containsKey(bodyPart))
            return Collections.EMPTY_SET;

        return weaponSlots.get(bodyPart).stream()
                .filter(weaponSlot -> weaponSlot.getWeapon().getStatus() != Destroyed)
                .map(WeaponSlot::getWeapon)
                .collect(Collectors.toSet());
    }

    @Override
    public boolean addWeapon(BodyPart bodyPart, Weapon weapon) {
        Optional<WeaponSlot> weaponSlot = weaponSlots.get(bodyPart)
                .stream()
                .filter(weaponSlot1 -> weaponSlot1.getWeapon() == null)
                .filter(weaponSlot1 -> weaponSlot1.getAllowedTypes().contains(weapon.getType()))
                .findAny();


        weaponSlot.ifPresent(weaponSlot1 -> {
            weaponSlot1.setWeapon(weapon);
        });

        return weaponSlot.isPresent();
    }

    @Override
    public Map<BodyPart, String> getDefinedBodyParts() {
        return bodyDefinition.entrySet().stream()
                .filter(entry -> entry.getValue().isPresent()).collect(Collectors.toMap(entry -> entry.getKey()
                        , entry -> entry.getValue().orElse(null)));
    }


    @Override
    public boolean canMoveAfterAttack() {
        return false;
    }

    @Override
    public boolean canFly() {
        return false;
    }

    public IsometricAnimatedSprite getIsometricSprite() {
        return isometricSprite;
    }
}
