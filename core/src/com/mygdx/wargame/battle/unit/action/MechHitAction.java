package com.mygdx.wargame.battle.unit.action;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.mygdx.wargame.battle.rules.calculator.DamageCalculator;
import com.mygdx.wargame.battle.rules.facade.Facades;
import com.mygdx.wargame.battle.rules.facade.GameState;
import com.mygdx.wargame.common.component.weapon.Weapon;
import com.mygdx.wargame.common.mech.BodyPart;

import static com.mygdx.wargame.battle.map.BattleMapStore.battleMap;

public class MechHitAction extends Action {

    private DamageCalculator damageCalculator = Facades.attackFacade.getDamageCalculator();
    private Weapon weapon;
    private BodyPart bodyPart;

    public MechHitAction(Weapon weapon, BodyPart bodyPart) {
        this.weapon = weapon;
        this.bodyPart = bodyPart;
    }

    @Override
    public boolean act(float delta) {
        damageCalculator.calculate(GameState.selectedPilot, GameState.selectedMech, GameState.target.get().getPilot(), GameState.target.get().getMech(), weapon, bodyPart);
        int stabilityAfterHit = Facades.attackFacade.getWeaponStabilityDecreaseCalculator().calculate(GameState.selectedPilot, GameState.selectedMech, GameState.target.get().getPilot(), GameState.target.get().getMech(), battleMap, weapon);
        GameState.target.get().getMech().setStability(GameState.target.get().getMech().getStability() - stabilityAfterHit);
        return true;
    }
}
