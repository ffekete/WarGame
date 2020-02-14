package com.mygdx.wargame.battle.combat;

import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.mygdx.wargame.battle.lock.ActionLock;
import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.battle.map.Node;
import com.mygdx.wargame.battle.screen.StageElementsStorage;
import com.mygdx.wargame.battle.unit.action.AttackAction;
import com.mygdx.wargame.battle.unit.action.AttackAnimationAction;
import com.mygdx.wargame.battle.unit.action.BulletAnimationAction;
import com.mygdx.wargame.battle.unit.action.LockAction;
import com.mygdx.wargame.battle.unit.action.MoveIntoRangeAction;
import com.mygdx.wargame.mech.AbstractMech;
import com.mygdx.wargame.pilot.Pilot;
import com.mygdx.wargame.rules.calculator.RangeCalculator;
import com.mygdx.wargame.rules.facade.AttackFacade;

public class RangedAttackTargetCalculator implements AttackCalculator {

    private BattleMap battleMap;
    private RangeCalculator rangeCalculator;
    private AttackFacade attackFacade;
    private ActionLock actionLock;
    private Stage stage;
    private Stage hudStage;
    private AssetManager assetManager;
    private StageElementsStorage stageElementsStorage;

    public RangedAttackTargetCalculator(BattleMap battleMap, RangeCalculator rangeCalculator, AttackFacade attackFacade, ActionLock actionLock, Stage stage, Stage hudStage, AssetManager assetManager, StageElementsStorage stageElementsStorage) {
        this.battleMap = battleMap;
        this.rangeCalculator = rangeCalculator;
        this.attackFacade = attackFacade;
        this.actionLock = actionLock;
        this.stage = stage;
        this.hudStage = hudStage;
        this.assetManager = assetManager;
        this.stageElementsStorage = stageElementsStorage;
    }

    @Override
    public void calculate(Pilot attackerPilot, AbstractMech attackerMech, AbstractMech defenderMech, Pilot defenderPilot) {

        if (attackerMech != null && defenderMech != null) {
            Node start = battleMap.getNodeGraphLv1().getNodeWeb()[(int) attackerMech.getX()][(int) attackerMech.getY()];
            Node end = battleMap.getNodeGraphLv1().getNodeWeb()[(int) defenderMech.getX()][(int) defenderMech.getY()];

            // reconnect so that attacker can move
            battleMap.getNodeGraphLv1().reconnectCities(battleMap.getNodeGraphLv1().getNodeWeb()[(int) attackerMech.getX()][(int) attackerMech.getY()]);

            GraphPath<Node> paths = battleMap.calculatePath(start, end);
            battleMap.addPath(attackerMech, paths);
            SequenceAction sequenceAction = new SequenceAction();

            sequenceAction.addAction(new LockAction(actionLock));
            sequenceAction.addAction(new MoveIntoRangeAction(battleMap, attackerMech, attackerPilot, defenderMech.getX(), defenderMech.getY(), rangeCalculator));

            ParallelAction parallelAction = new ParallelAction();

            parallelAction.addAction(new AttackAnimationAction(attackerMech, defenderMech, rangeCalculator.calculateAllWeaponsRange(attackerPilot, attackerMech)));
            parallelAction.addAction(new BulletAnimationAction(attackerMech, defenderMech, stage, assetManager, actionLock, rangeCalculator.calculateAllWeaponsRange(attackerPilot, attackerMech), stageElementsStorage, battleMap));

            sequenceAction.addAction(parallelAction);
            sequenceAction.addAction(new AttackAction(attackFacade, attackerMech, attackerPilot, defenderMech, defenderPilot, battleMap, rangeCalculator.calculateAllWeaponsRange(attackerPilot, attackerMech)));
            //sequenceAction.addAction(new UnlockAction(actionLock));
            attackerMech.addAction(sequenceAction);
        }
    }
}
