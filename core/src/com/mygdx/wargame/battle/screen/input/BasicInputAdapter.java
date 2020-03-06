package com.mygdx.wargame.battle.screen.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mygdx.wargame.battle.lock.ActionLock;
import com.mygdx.wargame.battle.screen.ScreenConfiguration;
import com.mygdx.wargame.battle.screen.StageElementsStorage;
import com.mygdx.wargame.battle.screen.TurnProcessingFacadeStore;
import com.mygdx.wargame.battle.screen.ui.HUDMediator;
import com.mygdx.wargame.common.component.weapon.Status;
import com.mygdx.wargame.common.component.weapon.Weapon;
import com.mygdx.wargame.config.Config;
import com.mygdx.wargame.common.mech.Mech;

import java.util.HashMap;
import java.util.Map;

import static com.mygdx.wargame.config.Config.*;
import static com.mygdx.wargame.config.Config.SCREEN_HUD_RATIO;

public class BasicInputAdapter extends InputAdapter {

    private ScreenConfiguration screenConfiguration;
    private ActionLock actionLock;
    private HUDMediator hudMediator;
    private TurnProcessingFacadeStore turnProcessingFacadeStore;
    private StageElementsStorage stageElementsStorage;
    private Map<Weapon, CheckBox> checkBoxMap = new HashMap<>();

    public BasicInputAdapter(ScreenConfiguration screenConfiguration, ActionLock actionLock, HUDMediator hudMediator, TurnProcessingFacadeStore turnProcessingFacadeStore, StageElementsStorage stageElementsStorage) {
        this.screenConfiguration = screenConfiguration;
        this.actionLock = actionLock;
        this.hudMediator = hudMediator;
        this.turnProcessingFacadeStore = turnProcessingFacadeStore;
        this.stageElementsStorage = stageElementsStorage;
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.SPACE) {
            Config.showDirectionMarkers = true;
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {

        if (keycode == Input.Keys.ESCAPE) {
            hudMediator.getBattleGameMenuFacade().toggle();
        }

        if(keycode == Input.Keys.SPACE) {
            Config.showDirectionMarkers = false;
        }

        if(keycode == Input.Keys.W) {
            addAllAvailableWeaponsToScrollPane(turnProcessingFacadeStore.getTurnProcessingFacade().getNext().getKey());
            hudMediator.getMechInfoPanelFacade().showWeaponSelectionPanel(hudMediator);
        }
        return true;
    }

    @Override
    public boolean scrolled(int amount) {
        screenConfiguration.zoom = amount * Gdx.graphics.getDeltaTime();
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {

        if (actionLock.isLocked())
            return true;

        if (screenY <= 10) {
            screenConfiguration.scrollY = 10 * Gdx.graphics.getDeltaTime();
        } else if (screenY >= SCREEN_SIZE_Y - 10) {
            screenConfiguration.scrollY = -10 * Gdx.graphics.getDeltaTime();

        } else {
            screenConfiguration.scrollY = 0;
        }

        if (screenX <= 10) {
            screenConfiguration.scrollX = -10 * Gdx.graphics.getDeltaTime();
        } else if (screenX >= SCREEN_SIZE_X - 10) {
            screenConfiguration.scrollX = 10 * Gdx.graphics.getDeltaTime();

        } else {
            screenConfiguration.scrollX = 0;
        }

        return false;
    }

    private void addAllAvailableWeaponsToScrollPane(Mech mec) {

        CheckBox.CheckBoxStyle checkBoxStyle = hudMediator.getMechInfoPanelFacade().getCheckBoxStyle();
        Label.LabelStyle labelStyle = hudMediator.getMechInfoPanelFacade().getSmallLabelStyle();

        checkBoxMap.clear();
        mec.getAllComponents().stream()
                .filter(c -> c.getStatus() != Status.Destroyed)
                .filter(c -> Weapon.class.isAssignableFrom(c.getClass()))
                .map(c -> ((Weapon) c))
                .forEach(w -> {
                    CheckBox checkBox = new CheckBox("  " + w.getShortName(), checkBoxStyle);
                    checkBoxMap.put(w, checkBox);
                    checkBox.setChecked(w.getStatus() == Status.Selected);
                    checkBox.addListener(new ChangeListener() {
                        @Override
                        public void changed(ChangeEvent event, Actor actor) {
                            w.setStatus(checkBox.isChecked() ? Status.Selected : Status.Active);
                        }
                    });
                    hudMediator.getMechInfoPanelFacade().getIbTable().add(checkBox).left().padLeft(20 / SCREEN_HUD_RATIO);

                    int ammo = w.getAmmo().orElse(-1);
                    hudMediator.getMechInfoPanelFacade().getIbTable().add(new Label(ammo < 0 ? "A: N/A" : "A: " + ammo, labelStyle)).padRight(15 / SCREEN_HUD_RATIO);
                    hudMediator.getMechInfoPanelFacade().getIbTable().add(new Label("H: " + w.getHeat(), labelStyle)).padRight(15 / SCREEN_HUD_RATIO);
                    hudMediator.getMechInfoPanelFacade().getIbTable().add(new Label("SD: " + w.getShieldDamage(), labelStyle)).padRight(15 / SCREEN_HUD_RATIO);
                    hudMediator.getMechInfoPanelFacade().getIbTable().add(new Label("AD: " + w.getArmorDamage(), labelStyle)).padRight(15 / SCREEN_HUD_RATIO);
                    hudMediator.getMechInfoPanelFacade().getIbTable().add(new Label("BD: " + w.getBodyDamage(), labelStyle)).padRight(15 / SCREEN_HUD_RATIO);
                    hudMediator.getMechInfoPanelFacade().getIbTable().add(new Label("x ( " + w.getDamageMultiplier() + " )", labelStyle));

                    hudMediator.getMechInfoPanelFacade().getIbTable().row();
                });

        stageElementsStorage.hudStage.setScrollFocus(hudMediator.getMechInfoPanelFacade().getWeaponSelectionContainer());
    }

}

