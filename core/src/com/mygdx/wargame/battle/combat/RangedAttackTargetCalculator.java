package com.mygdx.wargame.battle.combat;

import box2dLight.RayHandler;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.mygdx.wargame.battle.action.CenterCameraAction;
import com.mygdx.wargame.battle.action.ZoomOutCameraAction;
import com.mygdx.wargame.battle.lock.ActionLock;
import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.battle.map.Node;
import com.mygdx.wargame.battle.map.movement.MovementMarkerFactory;
import com.mygdx.wargame.battle.screen.StageElementsStorage;
import com.mygdx.wargame.battle.screen.ui.movement.WayPoint;
import com.mygdx.wargame.battle.unit.action.*;
import com.mygdx.wargame.mech.AbstractMech;
import com.mygdx.wargame.mech.BodyPart;
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
    private MoveActorAlongPathActionFactory moveActorAlongPathActionFactory;
    private MovementMarkerFactory movementMarkerFactory;
    private RayHandler rayHandler;

    public RangedAttackTargetCalculator(BattleMap battleMap, RangeCalculator rangeCalculator, AttackFacade attackFacade, ActionLock actionLock, Stage stage, Stage hudStage, AssetManager assetManager, StageElementsStorage stageElementsStorage, MovementMarkerFactory movementMarkerFactory, RayHandler rayHandler) {
        this.battleMap = battleMap;
        this.rangeCalculator = rangeCalculator;
        this.attackFacade = attackFacade;
        this.actionLock = actionLock;
        this.stage = stage;
        this.hudStage = hudStage;
        this.assetManager = assetManager;
        this.stageElementsStorage = stageElementsStorage;
        this.movementMarkerFactory = movementMarkerFactory;
        this.moveActorAlongPathActionFactory = new MoveActorAlongPathActionFactory(stageElementsStorage, this.movementMarkerFactory, assetManager);
        this.rayHandler = rayHandler;
    }

    @Override
    public void calculate(Pilot attackerPilot, AbstractMech attackerMech, AbstractMech defenderMech, Pilot defenderPilot, BodyPart targetedBodyPart) {

        if (attackerMech != null && defenderMech != null) {
            Node start = battleMap.getNodeGraphLv1().getNodeWeb()[(int) attackerMech.getX()][(int) attackerMech.getY()];
            Node end = battleMap.getNodeGraphLv1().getNodeWeb()[(int) defenderMech.getX()][(int) defenderMech.getY()];

            // reconnect so that attacker can move
            battleMap.getNodeGraphLv1().reconnectCities(battleMap.getNodeGraphLv1().getNodeWeb()[(int) attackerMech.getX()][(int) attackerMech.getY()]);

            GraphPath<Node> paths = battleMap.calculatePath(start, end);
            battleMap.addPath(attackerMech, paths);
            SequenceAction sequenceAction = new SequenceAction();

            sequenceAction.addAction(new LockAction(actionLock));

            CenterCameraAction centerCameraAction = new CenterCameraAction(stageElementsStorage, actionLock);
            centerCameraAction.setStartPosition(stageElementsStorage.stage.getCamera().position.x, stageElementsStorage.stage.getCamera().position.y);
            centerCameraAction.setDuration(1f);
            centerCameraAction.setPosition(attackerMech.getX(), attackerMech.getY());
            sequenceAction.addAction(centerCameraAction);

            for (int i = 1; i < paths.getCount(); i++) {
                WayPoint wayPoint = new WayPoint(assetManager, stageElementsStorage);
                wayPoint.setPosition(paths.get(i).getX(), paths.get(i).getY());

                sequenceAction.addAction(new AddWayPointAction(stageElementsStorage, wayPoint));
            }

            //sequenceAction.addAction(new MoveIntoRangeAction(battleMap, attackerMech, attackerPilot, defenderMech.getX(), defenderMech.getY(), rangeCalculator));
            sequenceAction.addAction(moveActorAlongPathActionFactory.act(paths, attackerMech, rangeCalculator.calculateAllWeaponsRange(attackerPilot, attackerMech), battleMap));

            sequenceAction.addAction(new RemoveMovementMarkersAction(stageElementsStorage, movementMarkerFactory));

            sequenceAction.addAction(new ZoomOutCameraAction(stageElementsStorage, attackerMech, defenderMech, (OrthographicCamera) stageElementsStorage.stage.getCamera()));

            ParallelAction parallelAction = new ParallelAction();

            sequenceAction.addAction(new ChangeDirectionAction(defenderMech.getX(), defenderMech.getY(), attackerMech));

            parallelAction.addAction(new AttackAnimationAction(attackerMech, defenderMech, rangeCalculator.calculateAllWeaponsRange(attackerPilot, attackerMech)));
            parallelAction.addAction(new BulletAnimationAction(attackerMech, defenderMech, stage, assetManager, actionLock, rangeCalculator.calculateAllWeaponsRange(attackerPilot, attackerMech), stageElementsStorage, battleMap, rayHandler));

            sequenceAction.addAction(parallelAction);
            sequenceAction.addAction(new AttackAction(attackFacade, attackerMech, attackerPilot, defenderMech, defenderPilot, battleMap, rangeCalculator.calculateAllWeaponsRange(attackerPilot, attackerMech), targetedBodyPart));
            sequenceAction.addAction(new RemoveWayPointAction(stageElementsStorage));
            attackerMech.addAction(sequenceAction);
        }
    }
}
