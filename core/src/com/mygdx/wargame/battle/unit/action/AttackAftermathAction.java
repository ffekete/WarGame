package com.mygdx.wargame.battle.unit.action;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.mygdx.wargame.battle.rules.facade.GameState;
import com.mygdx.wargame.common.mech.BodyPart;

public class AttackAftermathAction extends Action {
    @Override
    public boolean act(float delta) {

        GameState.selectedMech.setAttacked(true);
        if(GameState.selectedMech.canMoveAfterAttack() && GameState.selectedMech.getRemainingMovementPoints() > 0) {
            GameState.selectedMech.setMoved(false);
        } else {
            GameState.selectedMech.setMoved(true);
        }

        if (GameState.target.get().getMech().getHp(BodyPart.Torso) <= 0 || GameState.target.get().getMech().getHp(BodyPart.Head) <= 0) {
            GameState.target.get().getMech().setActive(false);
        }

        return true;
    }
}
