package com.mygdx.wargame.battle.input;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mygdx.wargame.battle.combat.RangedAttackTargetCalculator;
import com.mygdx.wargame.battle.lock.ActionLock;
import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.battle.screen.StageElementsStorage;
import com.mygdx.wargame.battle.screen.ui.HUDMediator;
import com.mygdx.wargame.battle.screen.ui.localmenu.MechInfoPanelFacade;
import com.mygdx.wargame.battle.unit.Team;
import com.mygdx.wargame.component.weapon.Status;
import com.mygdx.wargame.component.weapon.Weapon;
import com.mygdx.wargame.mech.Mech;
import com.mygdx.wargame.pilot.Pilot;
import com.mygdx.wargame.rules.facade.TurnProcessingFacade;
import com.mygdx.wargame.util.StageUtils;

import java.util.HashMap;
import java.util.Map;

import static com.mygdx.wargame.config.Config.SCREEN_HUD_RATIO;

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
    private Table healthOverlay;
    private BattleMap battleMap;
    private HUDMediator hudMediator;

    public MechClickInputListener(Mech defenderMech, Pilot defenderPilot, TurnProcessingFacade turnProcessingFacade, RangedAttackTargetCalculator rangedAttackTargetCalculator, ActionLock actionLock, Label.LabelStyle labelStyle, CheckBox.CheckBoxStyle checkBoxStyle, MechInfoPanelFacade mechInfoPanelFacade, Stage hudStage, Stage stage, StageElementsStorage stageElementsStorage, BattleMap battleMap, HUDMediator hudMediator) {
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
        this.hudMediator = hudMediator;
        healthOverlay = hudMediator.getHealthInfoPanelFacade().getPanel();
        this.battleMap = battleMap;
    }

    @Override
    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {

        hudMediator.getHealthInfoPanelFacade().getNameLabel().setText(mec.getName());
        hudMediator.getHealthInfoPanelFacade().setLocked(true);
        hudMediator.getHealthInfoPanelFacade().update(pilot, mec);
        event.stop();
    }

    @Override
    public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
        hudMediator.getHealthInfoPanelFacade().setLocked(false);
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

        hudMediator.getTargetingPanelFacade().hide();

        if (actionLock.isLocked())
            return true;

        if (mec.getTeam().equals(Team.own)) {

            if (!mechInfoPanelFacade.isLocalMenuVisible()) {

                mechInfoPanelFacade.getIbTable().clear();
                hudMediator.getPilotDetailsFacade().update(pilot);
                addSelectAllWeaponsCheckbox();
                updateDetailsButton();
                updateCloseMenuButton();
                updatePilotButton();
                updateWeaponSelectionButton();
                addAllAvailableWeaponsToScrollPane();

                hudMediator.getDetailsPageFacade().update(mec);

                mechInfoPanelFacade.getWeaponSelectionContainer().layout();

                mechInfoPanelFacade.showLocalMenu();
            } else {
                mechInfoPanelFacade.hideLocalMenu();
            }

        } else if (mec.getTeam().equals(Team.enemy)) {

            if (!hudMediator.getEnemyMechInfoPanelFacade().isLocalMenuVisible()) {
                updateCloseMenuButtonForEnemy();
                updateAttackButton();
                updateAimedAttackButton();
                hudMediator.getEnemyMechInfoPanelFacade().showLocalMenu();
            } else {
                hudMediator.getEnemyMechInfoPanelFacade().hideLocalMenu();
            }
        } else {
            // ???
        }

        event.stop();
        return true;
    }

    private void updateAttackButton() {

        hudMediator.getEnemyMechInfoPanelFacade().getAttackButton().setVisible(true);
        // Details button
        Vector2 newCoord = StageUtils.convertBetweenStages(stage, hudStage, mec.getX() + 0.25f, mec.getY() + 0.25f);
        hudMediator.getEnemyMechInfoPanelFacade().getAttackButton().setX(newCoord.x);
        hudMediator.getEnemyMechInfoPanelFacade().getAttackButton().setY(newCoord.y);

        hudMediator.getEnemyMechInfoPanelFacade().setAttackingMech(turnProcessingFacade.getNext().getKey());
        hudMediator.getEnemyMechInfoPanelFacade().setAttackingPilot(turnProcessingFacade.getNext().getValue());
        hudMediator.getEnemyMechInfoPanelFacade().setBattleMap(battleMap);
        hudMediator.getEnemyMechInfoPanelFacade().setDefendingPilot(pilot);
        hudMediator.getEnemyMechInfoPanelFacade().setDefendingMech(mec);
    }

    private void updateAimedAttackButton() {

        hudMediator.getEnemyMechInfoPanelFacade().getAimedAttackButton().setVisible(true);
        // Details button
        Vector2 newCoord = StageUtils.convertBetweenStages(stage, hudStage, mec.getX() + 0.25f, mec.getY() + 0.25f);
        hudMediator.getEnemyMechInfoPanelFacade().getAimedAttackButton().setX(newCoord.x);
        hudMediator.getEnemyMechInfoPanelFacade().getAimedAttackButton().setY(newCoord.y);

        hudMediator.getEnemyMechInfoPanelFacade().setAttackingMech(turnProcessingFacade.getNext().getKey());
        hudMediator.getEnemyMechInfoPanelFacade().setAttackingPilot(turnProcessingFacade.getNext().getValue());
        hudMediator.getEnemyMechInfoPanelFacade().setBattleMap(battleMap);
        hudMediator.getEnemyMechInfoPanelFacade().setDefendingPilot(pilot);
        hudMediator.getEnemyMechInfoPanelFacade().setDefendingMech(mec);
    }

    private void addAllAvailableWeaponsToScrollPane() {
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
                    mechInfoPanelFacade.getIbTable().add(checkBox).left().padLeft(20 / SCREEN_HUD_RATIO);

                    int ammo = w.getAmmo().orElse(-1);
                    mechInfoPanelFacade.getIbTable().add(new Label(ammo < 0 ? "A: N/A" : "A: " + ammo, labelStyle)).padRight(15 / SCREEN_HUD_RATIO);
                    mechInfoPanelFacade.getIbTable().add(new Label("H: " + w.getHeat(), labelStyle)).padRight(15 / SCREEN_HUD_RATIO);
                    mechInfoPanelFacade.getIbTable().add(new Label("SD: " + w.getShieldDamage(), labelStyle)).padRight(15 / SCREEN_HUD_RATIO);
                    mechInfoPanelFacade.getIbTable().add(new Label("AD: " + w.getArmorDamage(), labelStyle)).padRight(15 / SCREEN_HUD_RATIO);
                    mechInfoPanelFacade.getIbTable().add(new Label("BD: " + w.getBodyDamage(), labelStyle)).padRight(15 / SCREEN_HUD_RATIO);
                    mechInfoPanelFacade.getIbTable().add(new Label("x ( " + w.getDamageMultiplier() + " )", labelStyle));

                    mechInfoPanelFacade.getIbTable().row();
                });

        hudStage.setScrollFocus(mechInfoPanelFacade.getWeaponSelectionContainer());
    }

    public void updateDetailsButton() {
        mechInfoPanelFacade.getDetailsButton().setVisible(true);
        // Details button
        Vector2 newCoord = StageUtils.convertBetweenStages(stage, hudStage, mec.getX(), mec.getY());
        mechInfoPanelFacade.getDetailsButton().setX(newCoord.x);
        mechInfoPanelFacade.getDetailsButton().setY(newCoord.y);
    }

    private void updatePilotButton() {
        mechInfoPanelFacade.getPilotButton().setVisible(true);
        // Details button
        Vector2 newCoord = StageUtils.convertBetweenStages(stage, hudStage, mec.getX() + 0.5f, mec.getY());
        mechInfoPanelFacade.getPilotButton().setX(newCoord.x);
        mechInfoPanelFacade.getPilotButton().setY(newCoord.y);


    }

    private void updateCloseMenuButtonForEnemy() {
        hudMediator.getEnemyMechInfoPanelFacade().getHideMenuButton().setVisible(true);
        // Details button
        Vector2 newCoord = StageUtils.convertBetweenStages(stage, hudStage, mec.getX() + 0.25f, mec.getY() + 0.25f);
        hudMediator.getEnemyMechInfoPanelFacade().getHideMenuButton().setX(newCoord.x);
        hudMediator.getEnemyMechInfoPanelFacade().getHideMenuButton().setY(newCoord.y);
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
        mechInfoPanelFacade.getIbTable().add(checkBoxSelectAll).left().padLeft(20 / SCREEN_HUD_RATIO).row();
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