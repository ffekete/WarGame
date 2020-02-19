package com.mygdx.wargame.rules.facade;

import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.mygdx.wargame.battle.action.CenterCameraAction;
import com.mygdx.wargame.battle.action.ShowReduceHeatAction;
import com.mygdx.wargame.battle.lock.ActionLock;
import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.battle.map.Node;
import com.mygdx.wargame.battle.map.movement.MovementMarkerFactory;
import com.mygdx.wargame.battle.screen.StageElementsStorage;
import com.mygdx.wargame.battle.screen.ui.ScalableProgressBar;
import com.mygdx.wargame.battle.screen.ui.localmenu.MechInfoPanelFacade;
import com.mygdx.wargame.battle.unit.action.*;
import com.mygdx.wargame.mech.AbstractMech;
import com.mygdx.wargame.mech.Mech;
import com.mygdx.wargame.pilot.Pilot;
import com.mygdx.wargame.rules.calculator.HeatCalculator;
import com.mygdx.wargame.rules.calculator.MovementSpeedCalculator;
import com.mygdx.wargame.rules.calculator.RangeCalculator;
import com.mygdx.wargame.rules.facade.target.Target;
import com.mygdx.wargame.rules.facade.target.TargetingFacade;
import com.mygdx.wargame.util.MathUtils;
import com.mygdx.wargame.util.StageUtils;

import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

public class TurnProcessingFacade {

    private Map<Mech, Pilot> allSorted = new TreeMap<>();
    private ActionLock actionLock;
    private AttackFacade attackFacade;
    private TargetingFacade targetingFacade;
    private MovementSpeedCalculator movementSpeedCalculator;
    private Map<Mech, Pilot> team1;
    private Map<Mech, Pilot> team2;
    private Iterator<Map.Entry<Mech, Pilot>> iterator;
    Map.Entry<Mech, Pilot> next = null;
    private RangeCalculator rangeCalculator;
    private Stage stage;
    private Stage hudStage;
    private AssetManager assetManager;
    private StageElementsStorage stageElementsStorage;
    private MoveActorAlongPathActionFactory moveActorAlongPathActionFactory;
    private MovementMarkerFactory movementMarkerFactory;
    private HeatCalculator heatCalculator;
    private MechInfoPanelFacade mechInfoPanelFacade;

    public TurnProcessingFacade(ActionLock actionLock, AttackFacade attackFacade, TargetingFacade targetingFacade, MovementSpeedCalculator movementSpeedCalculator,
                                Map<Mech, Pilot> team1, Map<Mech, Pilot> team2, RangeCalculator rangeCalculator, Stage stage, Stage hudStage, AssetManager assetManager, StageElementsStorage stageElementsStorage, MovementMarkerFactory movementMarkerFactory, HeatCalculator heatCalculator, MechInfoPanelFacade mechInfoPanelFacade) {
        this.actionLock = actionLock;
        this.attackFacade = attackFacade;
        this.targetingFacade = targetingFacade;
        this.movementSpeedCalculator = movementSpeedCalculator;

        this.team1 = team1;
        this.team2 = team2;
        this.rangeCalculator = rangeCalculator;
        this.stage = stage;
        this.hudStage = hudStage;
        this.assetManager = assetManager;
        this.stageElementsStorage = stageElementsStorage;
        this.movementMarkerFactory = movementMarkerFactory;
        this.heatCalculator = heatCalculator;
        this.mechInfoPanelFacade = mechInfoPanelFacade;

        this.team1.forEach((key, value) -> allSorted.put(key, value));
        this.team2.forEach((key, value) -> allSorted.put(key, value));

        iterator = allSorted.entrySet().iterator();

        this.moveActorAlongPathActionFactory = new MoveActorAlongPathActionFactory(stageElementsStorage, this.movementMarkerFactory);
    }

    public Map.Entry<Mech, Pilot> getNext() {
        return next;
    }

    public void process(BattleMap battleMap, Stage stage) {

        if (actionLock.isLocked()) {
            return;
        }

        if (next != null && team1.containsKey(next.getKey()) && (!next.getKey().attacked() || !next.getKey().moved())) {
            return;
        }

        if (!iterator.hasNext()) {
            iterator = allSorted.entrySet().iterator();

            // reset moved and attacked statuses
            allSorted.keySet().forEach(mech -> {
                mech.setMoved(false);
                mech.setAttacked(false);
            });

            next = iterator.next();
            //centerCameraOnNext(stage);
        } else {
            next = iterator.next();
            //centerCameraOnNext(stage);
        }

        Mech selectedMech = next.getKey();
        Pilot selectedPilot = next.getValue();

        battleMap.removePath(next.getKey());

        if (!selectedMech.isActive()) {
            // skip, if deactivated
            if (iterator.hasNext()) {
                next = iterator.next();
            }
        } else if (team2.containsKey(selectedMech)) {

            SequenceAction sequenceAction = new SequenceAction();

            sequenceAction.reset();

            // lock all actions
            actionLock.setLocked(true);

            sequenceAction.addAction(new LockAction(actionLock));

            sequenceAction.addAction(centerCameraOnNext(stageElementsStorage));


            // reconnect graph so that attacker can move
            battleMap.getNodeGraphLv1().reconnectCities(battleMap.getNodeGraphLv1().getNodeWeb()[(int) selectedMech.getX()][(int) selectedMech.getY()]);

            // calculate movement points
            int movementPoints = movementSpeedCalculator.calculate(selectedPilot, selectedMech, battleMap);
            selectedMech.resetMovementPoints(movementPoints);

            sequenceAction.addAction(reduceHeatLevel(stageElementsStorage, mechInfoPanelFacade, selectedPilot, selectedMech, battleMap));

            // find target
            Optional<Target> target = targetingFacade.findTarget(selectedPilot, selectedMech, team1, battleMap);

            int minRange = rangeCalculator.calculateAllWeaponsRange(selectedPilot, selectedMech);

            // move if target too far away
            if (target.isPresent()) {
                if (target.get().getTargetNode() != null) {

                    // calculate path
                    GraphPath<Node> paths = battleMap.calculatePath(battleMap.getNodeGraphLv1().getNodeWeb()[(int) selectedMech.getX()][(int) selectedMech.getY()],
                            battleMap.getNodeGraphLv1().getNodeWeb()[(int) target.get().getTargetNode().getX()][(int) target.get().getTargetNode().getY()]);

                    battleMap.addPath(selectedMech, paths);

                    sequenceAction.addAction(moveActorAlongPathActionFactory.act(paths, (AbstractMech) selectedMech, 1, battleMap));
                    //sequenceAction.addAction(new MoveIntoFlankingRangeAction(battleMap, selectedMech, selectedPilot, target.get().getTargetNode().getX(), target.get().getTargetNode().getY(), rangeCalculator));

                } else if (MathUtils.getDistance(selectedMech.getX(), selectedMech.getY(), target.get().getMech().getX(), target.get().getMech().getY()) > minRange) {

                    // reconnect graph so that attacker can move
                    battleMap.getNodeGraphLv1().reconnectCities(battleMap.getNodeGraphLv1().getNodeWeb()[(int) selectedMech.getX()][(int) selectedMech.getY()]);

                    // calculate path
                    GraphPath<Node> paths = battleMap.calculatePath(battleMap.getNodeGraphLv1().getNodeWeb()[(int) selectedMech.getX()][(int) selectedMech.getY()],
                            battleMap.getNodeGraphLv1().getNodeWeb()[(int) target.get().getMech().getX()][(int) target.get().getMech().getY()]);

                    battleMap.addPath(selectedMech, paths);

                    sequenceAction.addAction(moveActorAlongPathActionFactory.act(paths, (AbstractMech) selectedMech, 0, battleMap));
                    //sequenceAction.addAction(new MoveIntoRangeAction(battleMap, selectedMech, selectedPilot, target.get().getMech().getX(), target.get().getMech().getY(), rangeCalculator));

                } else {
                    // obstacle again, no movement
                    battleMap.setPermanentObstacle(selectedMech.getX(), selectedMech.getY());
                }

                // then attack
                ParallelAction attackActions = new ParallelAction();
                attackActions.addAction(new ChangeDirectionAction(target.get().getMech().getX(), target.get().getMech().getY(), selectedMech));
                attackActions.addAction(new AttackAnimationAction(selectedMech, target.get().getMech(), minRange));
                attackActions.addAction(new BulletAnimationAction(selectedMech, target.get().getMech(), stage, assetManager, actionLock, minRange, stageElementsStorage, battleMap));
                AttackAction attackAction = new AttackAction(attackFacade, selectedMech, selectedPilot, target.get().getMech(), target.get().getPilot(), battleMap, minRange);
                sequenceAction.addAction(attackActions);
                sequenceAction.addAction(attackAction);

                ((AbstractMech) selectedMech).addAction(sequenceAction);
            }

        } else {
            // wait for "next" button press
            int movementPoints = movementSpeedCalculator.calculate(selectedPilot, selectedMech, battleMap);
            selectedMech.resetMovementPoints(movementPoints);

            SequenceAction sequenceAction = new SequenceAction();
            sequenceAction.addAction(new LockAction(actionLock));
            sequenceAction.addAction(new RemoveMovementMarkersAction(stageElementsStorage, movementMarkerFactory));
            sequenceAction.addAction(new AddMovementMarkersAction(stageElementsStorage, movementMarkerFactory, battleMap, selectedMech));
            sequenceAction.addAction(centerCameraOnNext(stageElementsStorage));
            sequenceAction.addAction(reduceHeatLevel(stageElementsStorage, mechInfoPanelFacade, selectedPilot, selectedMech, battleMap));
            sequenceAction.addAction(new UnlockAction(actionLock, ""));
            ((AbstractMech) selectedMech).addAction(sequenceAction);
        }

    }

    private Action centerCameraOnNext(StageElementsStorage stageElementsStorage) {
        CenterCameraAction centerCameraAction = new CenterCameraAction(stageElementsStorage, actionLock);
        centerCameraAction.setStartPosition(stageElementsStorage.stage.getCamera().position.x, stage.getCamera().position.y);
        centerCameraAction.setStartPosition(stageElementsStorage.stage.getCamera().position.x, stage.getCamera().position.y);
        centerCameraAction.setPosition(next.getKey().getX(), next.getKey().getY());
        centerCameraAction.setDuration(1);
        return centerCameraAction;
    }

    private Action reduceHeatLevel(StageElementsStorage stageElementsStorage, MechInfoPanelFacade mechInfoPanelFacade, Pilot pilot, Mech mech, BattleMap battleMap) {
        int reduceAmount = heatCalculator.calculateCooling(pilot, mech, battleMap);
        ProgressBar progressBar = new ScalableProgressBar(0, 100, 1f, false, mechInfoPanelFacade.getSmallHeatInfoProgressBarStyle(), 0.01f, mech.getX(), mech.getY() + 1f);
        //progressBar.setSize(64, 64);
        mech.setHeatLevel(Math.max(mech.getHeatLevel() - reduceAmount, 0));
        SequenceAction sequenceAction = new SequenceAction();
        sequenceAction.addAction(new AddActorAction(stageElementsStorage.stage, progressBar));
        sequenceAction.addAction(new ShowReduceHeatAction(mech.getHeatLevel(), Math.max(mech.getHeatLevel() - reduceAmount, 0f), progressBar));
        sequenceAction.addAction(new RemoveCustomActorAction(stageElementsStorage.stage, progressBar));
        return sequenceAction;
    }

    public boolean isNextPlayerControlled() {
        return this.next != null && this.team1.containsKey(this.next.getKey());
    }

}
