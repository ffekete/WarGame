package com.mygdx.wargame.battle.input;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mygdx.wargame.battle.combat.RangedAttackTargetCalculator;
import com.mygdx.wargame.battle.lock.ActionLock;
import com.mygdx.wargame.battle.screen.localmenu.MechInfoPanelFacade;
import com.mygdx.wargame.battle.unit.Team;
import com.mygdx.wargame.component.weapon.Status;
import com.mygdx.wargame.component.weapon.Weapon;
import com.mygdx.wargame.mech.AbstractMech;
import com.mygdx.wargame.mech.Mech;
import com.mygdx.wargame.pilot.Pilot;
import com.mygdx.wargame.rules.facade.TurnProcessingFacade;
import com.mygdx.wargame.util.StageUtils;

import java.util.HashMap;
import java.util.Map;

public class MechClickInputListener extends InputListener {

    private Mech mec;
    private Pilot pilot;
    private TurnProcessingFacade turnProcessingFacade;
    private RangedAttackTargetCalculator rangedAttackTargetCalculator;
    private ActionLock actionLock;
    private Label.LabelStyle labelStyle;
    private CheckBox.CheckBoxStyle checkBoxStyle;
    private Map<Weapon, CheckBox> checkBoxMap = new HashMap<>();
    private MechInfoPanelFacade mechInfoPanelFacade;
    private Stage hudStage;
    private Stage stage;

    public MechClickInputListener(Mech defenderMech, Pilot defenderPilot, TurnProcessingFacade turnProcessingFacade, RangedAttackTargetCalculator rangedAttackTargetCalculator, ActionLock actionLock, Label.LabelStyle labelStyle, CheckBox.CheckBoxStyle checkBoxStyle, MechInfoPanelFacade mechInfoPanelFacade, Stage hudStage, Stage stage) {
        this.mec = defenderMech;
        this.pilot = defenderPilot;
        this.turnProcessingFacade = turnProcessingFacade;
        this.rangedAttackTargetCalculator = rangedAttackTargetCalculator;
        this.actionLock = actionLock;
        this.labelStyle = labelStyle;
        this.checkBoxStyle = checkBoxStyle;
        this.mechInfoPanelFacade = mechInfoPanelFacade;
        this.hudStage = hudStage;

        this.stage = stage;
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        if (actionLock.isLocked())
            return true;

        if (mec.getTeam().equals(Team.own)) {

            if (!mechInfoPanelFacade.isLocalMenuVisible()) {

                mechInfoPanelFacade.getIbTable().clear();

                addSelectAllWeaponsCheckbox();
                updateDetailsButton();
                updateCloseMenuButton();
                updatePilotButton();
                updateWeaponSelectionButton();
                mechInfoPanelFacade.showLocalMenu();

                UpdateHeatBar();
                addAllAvailableWeaponsToScrollPane();
            } else {
                mechInfoPanelFacade.hideLocalMenu();
            }

        } else if (mec.getTeam().equals(Team.enemy)) {
            // attack
            mechInfoPanelFacade.hideLocalMenu();
            rangedAttackTargetCalculator.calculate(turnProcessingFacade.getNext().getValue(), (AbstractMech) turnProcessingFacade.getNext().getKey(), (AbstractMech) mec, pilot);
        } else {
            // ???
        }

        return true;
    }

    private void UpdateHeatBar() {
        mechInfoPanelFacade.getHeatProgressBar().setValue(mec.getHeatLevel());
    }

    private void addAllAvailableWeaponsToScrollPane() {
        checkBoxMap.clear();
        mec.getAllComponents().stream()
                .filter(c -> c.getStatus() != Status.Destroyed)
                .filter(c -> Weapon.class.isAssignableFrom(c.getClass()))
                .map(c -> ((Weapon) c))
                .forEach(w -> {
                    CheckBox checkBox = new CheckBox("  " + w.getName(), checkBoxStyle);
                    checkBoxMap.put(w, checkBox);
                    checkBox.setChecked(w.getStatus() == Status.Selected);
                    checkBox.addListener(new ChangeListener() {
                        @Override
                        public void changed(ChangeEvent event, Actor actor) {
                            w.setStatus(checkBox.isChecked() ? Status.Selected : Status.Active);
                        }
                    });
                    mechInfoPanelFacade.getIbTable().add(checkBox).padRight(20);

                    int ammo = w.getAmmo().orElse(-1);
                    mechInfoPanelFacade.getIbTable().add(new Label(ammo < 0 ? "A: N/A" : "A: " + ammo, labelStyle)).padRight(15);
                    mechInfoPanelFacade.getIbTable().add(new Label("H: " + w.getHeat(), labelStyle)).padRight(15);
                    mechInfoPanelFacade.getIbTable().add(new Label("SD: " + w.getShieldDamage(), labelStyle)).padRight(15);
                    mechInfoPanelFacade.getIbTable().add(new Label("AD: " + w.getArmorDamage(), labelStyle)).padRight(15);
                    mechInfoPanelFacade.getIbTable().add(new Label("BD: " + w.getBodyDamage(), labelStyle)).padRight(15);
                    mechInfoPanelFacade.getIbTable().add(new Label("x ( " + w.getDamageMultiplier() + " )", labelStyle));

                    mechInfoPanelFacade.getIbTable().row();
                });
    }

    private void updateDetailsButton() {
        mechInfoPanelFacade.getDetailsButton().setVisible(true);
        // Details button
        Vector2 newCoord = StageUtils.convertBetweenStages(stage, hudStage, mec.getX(), mec.getY());
        mechInfoPanelFacade.getDetailsButton().setX(newCoord.x);
        mechInfoPanelFacade.getDetailsButton().setY(newCoord.y);

        mechInfoPanelFacade.getBigInfoPanelContainer().setSize(0, 0);
        mechInfoPanelFacade.getBigInfoPanelContainer().setPosition(mechInfoPanelFacade.getDetailsButton().getX(), mechInfoPanelFacade.getDetailsButton().getY());
    }

    private void updatePilotButton() {
        mechInfoPanelFacade.getPilotButton().setVisible(true);
        // Details button
        Vector2 newCoord = StageUtils.convertBetweenStages(stage, hudStage, mec.getX(), mec.getY());
        mechInfoPanelFacade.getPilotButton().setX(newCoord.x);
        mechInfoPanelFacade.getPilotButton().setY(newCoord.y);


    }

    private void updateCloseMenuButton() {
        mechInfoPanelFacade.getHideMenuButton().setVisible(true);
        // Details button
        Vector2 newCoord = StageUtils.convertBetweenStages(stage, hudStage, mec.getX(), mec.getY());
        mechInfoPanelFacade.getHideMenuButton().setX(newCoord.x);
        mechInfoPanelFacade.getHideMenuButton().setY(newCoord.y);


    }

    private void updateWeaponSelectionButton() {
        mechInfoPanelFacade.getWeaponSelectionButton().setVisible(true);
        // Details button
        Vector2 newCoord = StageUtils.convertBetweenStages(stage, hudStage, mec.getX(), mec.getY());
        mechInfoPanelFacade.getWeaponSelectionButton().setX(newCoord.x);
        mechInfoPanelFacade.getWeaponSelectionButton().setY(newCoord.y);

        mechInfoPanelFacade.getWeaponSelectionContainer().setSize(0, 0);
        mechInfoPanelFacade.getWeaponSelectionContainer().setPosition(mechInfoPanelFacade.getWeaponSelectionButton().getX(), mechInfoPanelFacade.getWeaponSelectionButton().getY());


    }

    private void addSelectAllWeaponsCheckbox() {
        CheckBox checkBoxSelectAll = new CheckBox("  Select all", checkBoxStyle);
        mechInfoPanelFacade.getIbTable().add(checkBoxSelectAll).row();
        checkBoxSelectAll.setChecked(true);
        checkBoxSelectAll.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                mec.getAllComponents().stream()
                        .filter(c -> c.getStatus() != Status.Destroyed)
                        .filter(c -> Weapon.class.isAssignableFrom(c.getClass()))
                        .map(c -> ((Weapon) c))
                        .forEach(w -> {
                            w.setStatus(checkBoxSelectAll.isChecked() ? Status.Selected : Status.Active);
                            checkBoxMap.get(w).setChecked(checkBoxSelectAll.isChecked());

                        });
            }
        });
    }
}