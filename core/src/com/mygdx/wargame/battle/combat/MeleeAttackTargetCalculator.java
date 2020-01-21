package com.mygdx.wargame.battle.combat;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.battle.unit.AbstractWarrior;
import com.mygdx.wargame.battle.unit.Unit;
import com.mygdx.wargame.battle.unit.action.CalculatePathToUnit;
import com.mygdx.wargame.battle.unit.action.RotateUnit;

import java.util.*;

public class MeleeAttackTargetCalculator implements AttackCalculator {

    private BattleMap battleMap;
    private Stage stage;
    private ShapeRenderer shapeRenderer;

    public MeleeAttackTargetCalculator(BattleMap battleMap, Stage stage, ShapeRenderer shapeRenderer) {
        this.battleMap = battleMap;
        this.stage = stage;
        this.shapeRenderer = shapeRenderer;
    }

    @Override
    public void calculate(Unit attacker, Unit defender) {
        // list all defenders
        Set<AbstractWarrior> attackers = new HashSet<>(attacker.getAll());

        Set<AbstractWarrior> defenders = new HashSet<>(defender.getAll());
        Set<AbstractWarrior> selected = new HashSet<>(); // this is empty

        long startTime = System.currentTimeMillis();

        // rotate
        SequenceAction sequenceAction = new SequenceAction();
        sequenceAction.addAction(new RotateUnit(attacker, defender.getCenter(), battleMap, shapeRenderer, stage));
        sequenceAction.addAction(new CalculatePathToUnit(attackers, battleMap, defenders, shapeRenderer, stage));
        attacker.addAction(sequenceAction);
    }
}
