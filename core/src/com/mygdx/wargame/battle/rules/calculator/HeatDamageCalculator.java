package com.mygdx.wargame.battle.rules.calculator;

import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.mygdx.wargame.battle.lock.ActionLock;
import com.mygdx.wargame.battle.screen.StageElementsStorage;
import com.mygdx.wargame.battle.screen.ui.FontCreator;
import com.mygdx.wargame.common.component.weapon.Weapon;
import com.mygdx.wargame.common.mech.BodyPart;
import com.mygdx.wargame.common.mech.Mech;

import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class HeatDamageCalculator {

    private Label.LabelStyle labelStyle;
    private StageElementsStorage stageElementsStorage;

    public HeatDamageCalculator() {
        this.stageElementsStorage = stageElementsStorage;

        labelStyle = new Label.LabelStyle();
        labelStyle.font = FontCreator.getBitmapFont(13);
    }

    public void calculate(Mech targetMech, Weapon weapon, SequenceAction sequenceAction) {
        BodyPart bodyPartToDamage = (BodyPart) targetMech.getDefinedBodyParts().keySet().stream().filter(Objects::nonNull).toArray()[new Random().nextInt((int)targetMech.getDefinedBodyParts().keySet().stream().filter(Objects::nonNull).count())];

        int amount = new Random().nextInt(3) + 1;
        targetMech.setHp(bodyPartToDamage, targetMech.getHp(bodyPartToDamage) - amount);
    }

}
