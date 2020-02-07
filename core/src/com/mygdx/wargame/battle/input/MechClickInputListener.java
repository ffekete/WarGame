package com.mygdx.wargame.battle.input;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mygdx.wargame.battle.combat.RangedAttackTargetCalculator;
import com.mygdx.wargame.battle.lock.ActionLock;
import com.mygdx.wargame.battle.screen.ScreenElements;
import com.mygdx.wargame.battle.unit.Team;
import com.mygdx.wargame.component.weapon.Status;
import com.mygdx.wargame.component.weapon.Weapon;
import com.mygdx.wargame.mech.AbstractMech;
import com.mygdx.wargame.mech.Mech;
import com.mygdx.wargame.pilot.Pilot;
import com.mygdx.wargame.rules.facade.TurnProcessingFacade;

import java.util.HashMap;
import java.util.Map;

public class MechClickInputListener extends InputListener {

    private Mech mec;
    private Pilot pilot;
    private TurnProcessingFacade turnProcessingFacade;
    private RangedAttackTargetCalculator rangedAttackTargetCalculator;
    private ActionLock actionLock;
    private ScreenElements screenElements;
    private Label.LabelStyle labelStyle;
    private CheckBox.CheckBoxStyle checkBoxStyle;

    public MechClickInputListener(Mech defenderMech, Pilot defenderPilot, TurnProcessingFacade turnProcessingFacade, RangedAttackTargetCalculator rangedAttackTargetCalculator, ActionLock actionLock, ScreenElements screenElements, Label.LabelStyle labelStyle, CheckBox.CheckBoxStyle checkBoxStyle) {
        this.mec = defenderMech;
        this.pilot = defenderPilot;
        this.turnProcessingFacade = turnProcessingFacade;
        this.rangedAttackTargetCalculator = rangedAttackTargetCalculator;
        this.actionLock = actionLock;
        this.screenElements = screenElements;
        this.labelStyle = labelStyle;
        this.checkBoxStyle = checkBoxStyle;
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        if(actionLock.isLocked())
            return true;

        if (mec.getTeam().equals(Team.own)) {
            screenElements.getMechInfoPanel().setVisible(true);
            screenElements.getMechInfoPanel().getIbTable().clear();
            mec.getAllComponents().stream()
                    .filter(c -> c.getStatus() != Status.Destroyed)
                    .filter(c -> Weapon.class.isAssignableFrom(c.getClass()))
                    .map(c ->  ((Weapon)c))
                    .forEach(w -> {
                        CheckBox checkBox = new CheckBox("  " + w.getName(), checkBoxStyle);
                        checkBox.setChecked(w.getStatus() == Status.Selected);
                        checkBox.addListener(new ChangeListener() {
                            @Override
                            public void changed(ChangeEvent event, Actor actor) {
                                w.setStatus(checkBox.isChecked() ? Status.Selected : Status.Active);
                            }
                        });
                        screenElements.getMechInfoPanel().getIbTable().add(checkBox).padRight(20);

                        int ammo = w.getAmmo().orElse(-1);
                        screenElements.getMechInfoPanel().getIbTable().add(new Label(ammo < 0 ? "N/A" : "" + ammo, labelStyle));
                        screenElements.getMechInfoPanel().getIbTable().row();
                        System.out.println("a");
                    });

        } else if (mec.getTeam().equals(Team.enemy)) {
            screenElements.getMechInfoPanel().setVisible(false);
            // attack
            rangedAttackTargetCalculator.calculate(turnProcessingFacade.getNext().getValue(), (AbstractMech) turnProcessingFacade.getNext().getKey(), (AbstractMech) mec, pilot);
        } else {
            // ???
        }

        return true;
    }
}