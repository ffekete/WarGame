package com.mygdx.wargame.battle.input;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.mygdx.wargame.battle.combat.RangedAttackTargetCalculator;
import com.mygdx.wargame.battle.controller.SelectionController;
import com.mygdx.wargame.battle.lock.ActionLock;
import com.mygdx.wargame.mech.AbstractMech;
import com.mygdx.wargame.battle.unit.Team;

public class MechClickInputListener extends InputListener {

    private AbstractMech mec;
    private SelectionController selectionController;
    private RangedAttackTargetCalculator rangedAttackTargetCalculator;
    private ActionLock actionLock;

    public MechClickInputListener(AbstractMech mech, SelectionController selectionController, RangedAttackTargetCalculator rangedAttackTargetCalculator, ActionLock actionLock) {
        this.mec = mech;
        this.selectionController = selectionController;
        this.rangedAttackTargetCalculator = rangedAttackTargetCalculator;
        this.actionLock = actionLock;
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        if(actionLock.isLocked())
            return true;

        if (mec.getTeam().equals(Team.own)) {

            if (!selectionController.isSelected(mec))
                selectionController.select(mec);

        } else if (mec.getTeam().equals(Team.enemy)) {
            // attack
            rangedAttackTargetCalculator.calculate(selectionController.getSelected(), mec);
        } else {
            // ???
        }

        return true;
    }
}