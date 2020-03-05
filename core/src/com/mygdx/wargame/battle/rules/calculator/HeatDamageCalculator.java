package com.mygdx.wargame.battle.rules.calculator;

import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.mygdx.wargame.battle.action.ShowMessageActor;
import com.mygdx.wargame.battle.lock.ActionLock;
import com.mygdx.wargame.battle.screen.StageElementsStorage;
import com.mygdx.wargame.battle.screen.ui.FontCreator;
import com.mygdx.wargame.common.component.weapon.Weapon;
import com.mygdx.wargame.common.mech.BodyPart;
import com.mygdx.wargame.common.mech.Mech;

import java.util.Arrays;
import java.util.Random;

public class HeatDamageCalculator {

    private Label.LabelStyle labelStyle;
    private StageElementsStorage stageElementsStorage;
    private ActionLock actionLock;

    public HeatDamageCalculator(StageElementsStorage stageElementsStorage, ActionLock actionLock) {
        this.stageElementsStorage = stageElementsStorage;
        this.actionLock = actionLock;
        labelStyle = new Label.LabelStyle();
        labelStyle.font = FontCreator.getBitmapFont(13);
    }

    public void calculate(Mech targetMech, Weapon weapon, SequenceAction sequenceAction) {
        BodyPart bodyPartToDamage = Arrays.stream(BodyPart.values())
                .filter(bodyPart -> targetMech.getSelectedWeapons().stream().anyMatch(w -> w == weapon))
                .findFirst()
                .get();

        int amount = new Random().nextInt(3) + 1;
        targetMech.setHp(bodyPartToDamage, targetMech.getHp(bodyPartToDamage) - amount);

        sequenceAction.addAction(new ShowMessageActor(labelStyle, targetMech.getX(), targetMech.getY(), "Damaged " + bodyPartToDamage.getName() + " for " + amount + " damage", stageElementsStorage, actionLock));
    }

}
