package com.mygdx.wargame.input;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.mygdx.wargame.battle.combat.MeleeAttackTargetCalculator;
import com.mygdx.wargame.battle.controller.SelectionController;
import com.mygdx.wargame.battle.unit.Man;
import com.mygdx.wargame.battle.unit.Team;

public class ManInputListener extends InputListener {

    private Man man;
    private SelectionController selectionController;
    private MeleeAttackTargetCalculator meleeAttackTargetCalculator;

    public ManInputListener(Man man, SelectionController selectionController, MeleeAttackTargetCalculator meleeAttackTargetCalculator) {
        this.man = man;
        this.selectionController = selectionController;
        this.meleeAttackTargetCalculator = meleeAttackTargetCalculator;
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

        if (man.getUnit().getTeam().equals(Team.own)) {

            if (selectionController.isSelected(man))
                selectionController.deselectUnit(man.getUnit());
            else
                selectionController.selectUnit(man.getUnit());
        } else if (man.getUnit().getTeam().equals(Team.enemy)) {
            // attack
            meleeAttackTargetCalculator.calculate(selectionController.selectedUnit(), man.getUnit());
        } else {
            // ???
        }

        return true;
    }
}