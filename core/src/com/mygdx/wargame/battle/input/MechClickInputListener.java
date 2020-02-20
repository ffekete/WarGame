package com.mygdx.wargame.battle.input;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mygdx.wargame.battle.combat.RangedAttackTargetCalculator;
import com.mygdx.wargame.battle.lock.ActionLock;
import com.mygdx.wargame.battle.screen.StageElementsStorage;
import com.mygdx.wargame.battle.screen.ui.localmenu.MechInfoPanelFacade;
import com.mygdx.wargame.battle.ui.HealthOverlay;
import com.mygdx.wargame.battle.unit.Team;
import com.mygdx.wargame.component.armor.Armor;
import com.mygdx.wargame.component.weapon.Status;
import com.mygdx.wargame.component.weapon.Weapon;
import com.mygdx.wargame.mech.AbstractMech;
import com.mygdx.wargame.mech.BodyPart;
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
    private StageElementsStorage stageElementsStorage;
    private HealthOverlay healthOverlay;
    boolean overlayShown = false;

    public MechClickInputListener(Mech defenderMech, Pilot defenderPilot, TurnProcessingFacade turnProcessingFacade, RangedAttackTargetCalculator rangedAttackTargetCalculator, ActionLock actionLock, Label.LabelStyle labelStyle, CheckBox.CheckBoxStyle checkBoxStyle, MechInfoPanelFacade mechInfoPanelFacade, Stage hudStage, Stage stage, StageElementsStorage stageElementsStorage) {
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
        this.stageElementsStorage = stageElementsStorage;
        healthOverlay = mechInfoPanelFacade.getHealthOverlayImage();
    }

    @Override
    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
        if(!actionLock.isLocked() && !overlayShown && !mechInfoPanelFacade.isLocalMenuVisible()) {
            healthOverlay.setPosition(mec.getX() - 0.9f, mec.getY() - 0.5f);
            healthOverlay.setHeadHealth("" + mec.getHp(BodyPart.Head));
            healthOverlay.setLeftArmHealth("" + mec.getHp(BodyPart.LeftHand));
            healthOverlay.setLeftLegHealth("" + mec.getHp(BodyPart.LeftLeg));
            healthOverlay.setRightArmHealth("" + mec.getHp(BodyPart.RightHand));
            healthOverlay.setRightLegHealth("" + mec.getHp(BodyPart.RightLeg));
            healthOverlay.setTorsoHealth("" + mec.getHp(BodyPart.Torso));

            healthOverlay.setHeadArmor("" + mec.getComponents(BodyPart.Head).stream().filter(c -> Armor.class.isAssignableFrom(c.getClass())).map(a -> ((Armor)a).getHitPoint()).reduce((a,b) -> a + b).orElse(0));
            healthOverlay.setLeftLegArmor("" + mec.getComponents(BodyPart.LeftLeg).stream().filter(c -> Armor.class.isAssignableFrom(c.getClass())).map(a -> ((Armor)a).getHitPoint()).reduce((a,b) -> a + b).orElse(0));
            healthOverlay.setRightLegArmor("" + mec.getComponents(BodyPart.RightLeg).stream().filter(c -> Armor.class.isAssignableFrom(c.getClass())).map(a -> ((Armor)a).getHitPoint()).reduce((a,b) -> a + b).orElse(0));
            healthOverlay.setLeftArmArmor("" + mec.getComponents(BodyPart.LeftHand).stream().filter(c -> Armor.class.isAssignableFrom(c.getClass())).map(a -> ((Armor)a).getHitPoint()).reduce((a,b) -> a + b).orElse(0));
            healthOverlay.setRightArmArmor("" + mec.getComponents(BodyPart.RightHand).stream().filter(c -> Armor.class.isAssignableFrom(c.getClass())).map(a -> ((Armor)a).getHitPoint()).reduce((a,b) -> a + b).orElse(0));
            healthOverlay.setTorsoArmor("" + mec.getComponents(BodyPart.Torso).stream().filter(c -> Armor.class.isAssignableFrom(c.getClass())).map(a -> ((Armor)a).getHitPoint()).reduce((a,b) -> a + b).orElse(0));

            stageElementsStorage.airLevel.addActor(healthOverlay);
            this.overlayShown = true;
            event.stop();
        }
    }

    @Override
    public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
        if(overlayShown) {
            healthOverlay.setSize(0f, 0f);
            stageElementsStorage.airLevel.removeActor(healthOverlay);
            overlayShown = false;
            event.stop();
        }
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
                UpdateHeatBar();
                addAllAvailableWeaponsToScrollPane();

                mechInfoPanelFacade.getMechInfoTable().clear();
                mechInfoPanelFacade.getMechInfoTable().add(new Label("H : "+ mec.getHp(BodyPart.Head) + "/" + mec.getHeadMaxHp(), labelStyle)).center().pad(5);
                mechInfoPanelFacade.getMechInfoTable().add();
                mechInfoPanelFacade.getMechInfoTable().add();
                mechInfoPanelFacade.getMechInfoTable().add();
                mechInfoPanelFacade.getMechInfoTable().add(new Label("T : " + mec.getHp(BodyPart.Torso) + "/" + mec.getTorsoMaxHp(), labelStyle)).center().pad(5).row();

                mechInfoPanelFacade.getMechInfoTable().add(new Label("LH: " + mec.getHp(BodyPart.LeftHand) + "/" + mec.getLeftHandMaxHp(), labelStyle)).center().pad(5);
                mechInfoPanelFacade.getMechInfoTable().add();
                mechInfoPanelFacade.getMechInfoTable().add();
                mechInfoPanelFacade.getMechInfoTable().add();
                mechInfoPanelFacade.getMechInfoTable().add(new Label("RH: " + mec.getHp(BodyPart.RightHand) + "/" + mec.getRightHandMaxHp(), labelStyle)).center().pad(5).row();

                mechInfoPanelFacade.getMechInfoTable().add(new Label("LL: " + mec.getHp(BodyPart.LeftLeg) + "/" + mec.getLeftLegMaxHp(), labelStyle)).center().pad(5);
                mechInfoPanelFacade.getMechInfoTable().add();
                mechInfoPanelFacade.getMechInfoTable().add();
                mechInfoPanelFacade.getMechInfoTable().add();
                mechInfoPanelFacade.getMechInfoTable().add(new Label("RL: " + mec.getHp(BodyPart.RightLeg) + "/" + mec.getRightLegMaxHp(), labelStyle)).pad(5).center();

                mechInfoPanelFacade.showLocalMenu();
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

        event.stop();
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
        Vector2 newCoord = StageUtils.convertBetweenStages(stage, hudStage, mec.getX() + 0.5f, mec.getY());
        mechInfoPanelFacade.getPilotButton().setX(newCoord.x);
        mechInfoPanelFacade.getPilotButton().setY(newCoord.y);


    }

    private void updateCloseMenuButton() {
        mechInfoPanelFacade.getHideMenuButton().setVisible(true);
        // Details button
        Vector2 newCoord = StageUtils.convertBetweenStages(stage, hudStage, mec.getX() + 0.25f, mec.getY() + 0.25f);
        mechInfoPanelFacade.getHideMenuButton().setX(newCoord.x);
        mechInfoPanelFacade.getHideMenuButton().setY(newCoord.y);


    }

    private void updateWeaponSelectionButton() {
        mechInfoPanelFacade.getWeaponSelectionButton().setVisible(true);
        // Details button
        Vector2 newCoord = StageUtils.convertBetweenStages(stage, hudStage, mec.getX() + 0.25f, mec.getY());
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