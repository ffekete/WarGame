package com.mygdx.wargame.input;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.mygdx.wargame.battle.combat.MeleeAttackTargetCalculator;
import com.mygdx.wargame.battle.controller.SelectionController;
import com.mygdx.wargame.battle.unit.AbstractMech;
import com.mygdx.wargame.battle.unit.Team;

public class MechClickInputListener extends InputListener {

    private AbstractMech man;
    private SelectionController selectionController;
    private MeleeAttackTargetCalculator meleeAttackTargetCalculator;

    public MechClickInputListener(AbstractMech man, SelectionController selectionController, MeleeAttackTargetCalculator meleeAttackTargetCalculator) {
        this.man = man;
        this.selectionController = selectionController;
        this.meleeAttackTargetCalculator = meleeAttackTargetCalculator;
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        if (man.getTeam().equals(Team.own)) {

            if (!selectionController.isSelected(man))
                selectionController.select(man);

        } else if (man.getTeam().equals(Team.enemy)) {
            // attack
            meleeAttackTargetCalculator.calculate(selectionController.getSelected(), man);
        } else {
            // ???
        }

        return true;
    }
}