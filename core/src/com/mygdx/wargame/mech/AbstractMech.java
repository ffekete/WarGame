package com.mygdx.wargame.mech;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.battle.map.overlay.TileOverlayType;
import com.mygdx.wargame.battle.unit.Direction;
import com.mygdx.wargame.battle.unit.State;
import com.mygdx.wargame.battle.unit.Team;
import com.mygdx.wargame.component.armor.Armor;
import com.mygdx.wargame.component.shield.Shield;
import com.mygdx.wargame.component.weapon.Status;

import java.util.Random;

public abstract class
AbstractMech extends Actor implements Mech {

    private Team team;
    private int initiative;
    private int step = 1;
    private int shieldStep = new Random().nextInt(6);
    private int slow = 0;
    private float idleDelay = 0;
    private State state = State.Idle;
    private Direction direction = Direction.Left;
    private float range = 15f;
    private int heatLevel;
    private int stability;
    private boolean attacked;
    private boolean moved;
    private boolean active;
    private AssetManager assetManager;
    private BattleMap battleMap;

    protected TextureRegion mechTextureRegion;
    private TextureRegion shieldTextureRegion;
    private TextureRegion selectionTexture;

    public AbstractMech(int initiative, AssetManager assetManager, BattleMap battleMap) {
        this.initiative = initiative;
        this.assetManager = assetManager;
        this.shieldTextureRegion = new TextureRegion(assetManager.get("Shielded.png", Texture.class));
        this.battleMap = battleMap;
    }

    public void setState(State state) {

        if (this.state != state) {
            step = state.getStart();
            slow = 0;
        }

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
        this.selectionTexture = new TextureRegion(team == Team.own ? assetManager.get("FriendlyMarker.png", Texture.class) : assetManager.get("EnemyMarker.png", Texture.class));
    }

    @Override
    public void draw(float x, float y, SpriteBatch spriteBatch, TextureRegion texture) {

        if (step < state.getStart())
            step = state.getStart();

        if (step > state.getEnd())
            step = state.getStart();

        if (state != State.Idle) {
            if (slow == 0) {
                step++;
                if (step >= state.getEnd()) {
                    if (state != State.Dead && state != State.Jump)
                        step = state.getStart();
                    else
                        step = state.getEnd() - 1;
                }
                if (step < state.getStart())
                    step = state.getStart();
            }
        } else {
            if (idleDelay == 0 && state == State.Idle) {
                if (slow == 0) {
                    step++;
                    if (step >= state.getEnd()) {
                        step = state.getStart();
                        idleDelay = 1;
                    }
                    if (step < state.getStart())
                        step = state.getStart();
                }
            } else {
                idleDelay = (idleDelay + 1) % 400;
            }
        }

        slow++;
        if (slow == 11)
            slow = 0;

        if (slow == 0) {
            shieldStep = (shieldStep + 1) % 6;
        }

        if (battleMap.getNodeGraphLv1().getNodeWeb()[(int) getX()][(int) getY()].getGroundOverlay() != null &&
                battleMap.getNodeGraphLv1().getNodeWeb()[(int) getX()][(int) getY()].getGroundOverlay().getTileOverlayType() == TileOverlayType.Water && state != State.Jump) {
            texture.setRegion(direction.getOffset() * 16 + step * 16, 0, 16, 10);
        } else {
            texture.setRegion(direction.getOffset() * 16 + step * 16, 0, 16, 16);
        }
        texture.flip(direction.isMirrored(), false);
        spriteBatch.setColor(Color.valueOf("FFFFFF"));

        if (battleMap.getNodeGraphLv1().getNodeWeb()[(int) getX()][(int) getY()].getGroundOverlay() != null &&
                battleMap.getNodeGraphLv1().getNodeWeb()[(int) getX()][(int) getY()].getGroundOverlay().getTileOverlayType() == TileOverlayType.Water && state != State.Jump) {
            spriteBatch.draw(texture, x, y + 0.3f, 1, 0.6f);
        } else {
            spriteBatch.draw(texture, x, y, 1, 1);
        }


        spriteBatch.setColor(Color.valueOf("FFFFFF99"));
        spriteBatch.draw(selectionTexture, x, y, 1, 1);

        if (getShieldValue() > 0) {
            spriteBatch.setColor(Color.valueOf("FFFFFF55"));
            shieldTextureRegion.setRegion((shieldStep % 6) * 16, 0, 16, 16);
            spriteBatch.draw(shieldTextureRegion, x - 0.2f, y - 0.2f, 1.4f, 1.4f);
            spriteBatch.setColor(Color.WHITE);
        }
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
    public void setCoordinates(float x, float y) {
        super.setPosition(x, y);
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
}
