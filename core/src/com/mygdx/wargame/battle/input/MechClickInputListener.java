package com.mygdx.wargame.battle.input;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.mygdx.wargame.battle.combat.RangedAttackTargetCalculator;
import com.mygdx.wargame.battle.lock.ActionLock;
import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.battle.unit.Team;
import com.mygdx.wargame.mech.AbstractMech;
import com.mygdx.wargame.mech.Mech;
import com.mygdx.wargame.pilot.Pilot;
import com.mygdx.wargame.rules.facade.TurnProcessingFacade;

import java.util.Map;

public class MechClickInputListener extends InputListener {

    private Mech mec;
    private Pilot pilot;
    private TurnProcessingFacade turnProcessingFacade;
    private RangedAttackTargetCalculator rangedAttackTargetCalculator;
    private ActionLock actionLock;

    public MechClickInputListener(Mech defenderMech, Pilot defenderPilot, TurnProcessingFacade turnProcessingFacade, RangedAttackTargetCalculator rangedAttackTargetCalculator, ActionLock actionLock) {
        this.mec = defenderMech;
        this.pilot = defenderPilot;
        this.turnProcessingFacade = turnProcessingFacade;
        this.rangedAttackTargetCalculator = rangedAttackTargetCalculator;
        this.actionLock = actionLock;
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        if(actionLock.isLocked())
            return true;

        if (mec.getTeam().equals(Team.own)) {

        } else if (mec.getTeam().equals(Team.enemy)) {
            // attack
            rangedAttackTargetCalculator.calculate(turnProcessingFacade.getNext().getValue(), (AbstractMech) turnProcessingFacade.getNext().getKey(), (AbstractMech) mec, pilot);
        } else {
            // ???
        }

        return true;
    }
}