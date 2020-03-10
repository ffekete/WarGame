package com.mygdx.wargame.battle.rules.facade;

import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.DelayAction;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.mygdx.wargame.battle.action.CenterCameraAction;
import com.mygdx.wargame.battle.action.IntAction;
import com.mygdx.wargame.battle.action.ZoomOutCameraAction;
import com.mygdx.wargame.battle.lock.ActionLock;
import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.battle.map.Node;
import com.mygdx.wargame.battle.rules.calculator.HeatCalculator;
import com.mygdx.wargame.battle.rules.calculator.MovementSpeedCalculator;
import com.mygdx.wargame.battle.rules.calculator.RangeCalculator;
import com.mygdx.wargame.battle.rules.calculator.StabilityDecreaseCalculator;
import com.mygdx.wargame.battle.rules.facade.target.Target;
import com.mygdx.wargame.battle.rules.facade.target.TargetingFacade;
import com.mygdx.wargame.battle.screen.StageElementsStorage;
import com.mygdx.wargame.battle.screen.ui.HUDMediator;
import com.mygdx.wargame.battle.action.MoveActorAlongPathActionFactory;
import com.mygdx.wargame.battle.unit.action.*;
import com.mygdx.wargame.common.component.shield.Shield;
import com.mygdx.wargame.common.mech.AbstractMech;
import com.mygdx.wargame.common.mech.Mech;
import com.mygdx.wargame.common.pilot.Pilot;
import com.mygdx.wargame.util.MathUtils;

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
    private AssetManager assetManager;
    private StageElementsStorage stageElementsStorage;
    private MoveActorAlongPathActionFactory moveActorAlongPathActionFactory;
    private HeatCalculator heatCalculator;
    private WeaponSelectionOptimizer weaponSelectionOptimizer;
    private StabilityDecreaseCalculator stabilityDecreaseCalculator;
    private HUDMediator hudMediator;

    public TurnProcessingFacade(ActionLock actionLock, AttackFacade attackFacade, TargetingFacade targetingFacade, MovementSpeedCalculator movementSpeedCalculator,
                                Map<Mech, Pilot> team1, Map<Mech, Pilot> team2, RangeCalculator rangeCalculator, Stage stage, AssetManager assetManager, StageElementsStorage stageElementsStorage, HeatCalculator heatCalculator, StabilityDecreaseCalculator stabilityDecreaseCalculator, HUDMediator hudMediator) {
        this.actionLock = actionLock;
        this.attackFacade = attackFacade;
        this.targetingFacade = targetingFacade;
        this.movementSpeedCalculator = movementSpeedCalculator;
        this.hudMediator = hudMediator;

        this.team1 = team1;
        this.team2 = team2;
        this.rangeCalculator = rangeCalculator;
        this.stage = stage;
        this.assetManager = assetManager;
        this.stageElementsStorage = stageElementsStorage;
        this.heatCalculator = heatCalculator;
        this.stabilityDecreaseCalculator = stabilityDecreaseCalculator;

        this.team1.forEach((key, value) -> allSorted.put(key, value));
        this.team2.forEach((key, value) -> allSorted.put(key, value));

        iterator = allSorted.entrySet().iterator();

        this.moveActorAlongPathActionFactory = new MoveActorAlongPathActionFactory();

        this.weaponSelectionOptimizer = new WeaponSelectionOptimizer();
    }

    public Map.Entry<Mech, Pilot> getNext() {
        return next;
    }

    public void process(BattleMap battleMap, Stage stage) {
        //System.out.println(Gdx.graphics.getFramesPerSecond());

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

        if (team2.keySet().stream().noneMatch(Mech::isActive)) {

        } else if (team1.keySet().stream().noneMatch(Mech::isActive)) {

        } else {

            //hudMediator.getHudElementsFacade().update();

            Mech selectedMech = next.getKey();
            Pilot selectedPilot = next.getValue();

            if (!selectedMech.isActive()) {
                // skip, if deactivated
                if (iterator.hasNext()) {
                    next = iterator.next();
                }
            } else if (team2.containsKey(selectedMech)) {

                weaponSelectionOptimizer.doIt(selectedMech);

                SequenceAction sequenceAction = new SequenceAction();

                sequenceAction.reset();

                // lock all actions
                sequenceAction.addAction(new LockAction(actionLock));

                sequenceAction.addAction(centerCameraOnNext(stageElementsStorage));

                // reconnect graph so that attacker can move
                battleMap.getNodeGraph().reconnectCities(battleMap.getNodeGraph().getNodeWeb()[(int) selectedMech.getX()][(int) selectedMech.getY()]);

                // calculate movement points
                int movementPoints = movementSpeedCalculator.calculate(selectedPilot, selectedMech, battleMap);
                selectedMech.resetMovementPoints(movementPoints);

                sequenceAction.addAction(reduceHeatLevel(selectedPilot, selectedMech, battleMap));
                sequenceAction.addAction(regenerateShields(selectedMech));
                sequenceAction.addAction(new DelayAction(0.5f));
                sequenceAction.addAction(reduceStabilityLevel(selectedMech, battleMap));

                // find target
                Optional<Target> target = targetingFacade.findTarget(selectedPilot, selectedMech, team1, battleMap);

                int minRange = rangeCalculator.calculateAllWeaponsRange(selectedPilot, selectedMech);

                // move if target too far away
                if (target.isPresent()) {
                    if (target.get().getTargetNode() != null) {
                        System.out.println("Found target node");

                        // calculate path
                        GraphPath<Node> paths = battleMap.calculatePath(battleMap.getNodeGraph().getNodeWeb()[(int) selectedMech.getX()][(int) selectedMech.getY()],
                                battleMap.getNodeGraph().getNodeWeb()[(int) target.get().getTargetNode().getX()][(int) target.get().getTargetNode().getY()]);

                        sequenceAction.addAction(moveActorAlongPathActionFactory.getMovementAction(paths, (AbstractMech) selectedMech));

                    } else if (MathUtils.getDistance(selectedMech.getX(), selectedMech.getY(), target.get().getMech().getX(), target.get().getMech().getY()) > minRange) {
                        // reconnect graph so that attacker can move
                        battleMap.getNodeGraph().reconnectCities(battleMap.getNodeGraph().getNodeWeb()[(int) selectedMech.getX()][(int) selectedMech.getY()]);

                        // calculate path
                        GraphPath<Node> paths = battleMap.calculatePath(battleMap.getNodeGraph().getNodeWeb()[(int) selectedMech.getX()][(int) selectedMech.getY()],
                                battleMap.getNodeGraph().getNodeWeb()[(int) target.get().getMech().getX()][(int) target.get().getMech().getY()]);

                        sequenceAction.addAction(moveActorAlongPathActionFactory.getMovementAction(paths, (AbstractMech) selectedMech));

                    } else {
                        // obstacle again, no movement
                        battleMap.setTemporaryObstacle(selectedMech.getX(), selectedMech.getY());
                    }


                    if (target.get().getMech() != null) {
                        sequenceAction.addAction(new ZoomOutCameraAction(stageElementsStorage, selectedMech, target.get().getMech(), (OrthographicCamera) stage.getCamera()));

                        // then attack
                        ParallelAction attackActions = new ParallelAction();
                        attackActions.addAction(new ChangeDirectionAction(target.get().getMech().getX(), target.get().getMech().getY(), selectedMech));
                        attackActions.addAction(new AttackAnimationAction(selectedMech, target.get().getMech(), minRange));
                        //attackActions.addAction(new BulletAnimationAction(selectedMech, target.get().getMech(), stage, assetManager, actionLock, minRange, stageElementsStorage, battleMap));
                        AttackAction attackAction = new AttackAction(attackFacade, selectedMech, selectedPilot, target.get().getMech(), target.get().getPilot(), battleMap, minRange, null);
                        sequenceAction.addAction(attackActions);
                        sequenceAction.addAction(attackAction);
                    }
                    sequenceAction.addAction(new UnlockAction(actionLock, ""));

                    ((AbstractMech) selectedMech).addAction(sequenceAction);
                }

            } else {
                // wait for "next" button press
                int movementPoints = movementSpeedCalculator.calculate(selectedPilot, selectedMech, battleMap);
                selectedMech.resetMovementPoints(movementPoints);

                SequenceAction sequenceAction = new SequenceAction();
                sequenceAction.addAction(new LockAction(actionLock));
                sequenceAction.addAction(centerCameraOnNext(stageElementsStorage));
                sequenceAction.addAction(reduceHeatLevel(selectedPilot, selectedMech, battleMap));
                sequenceAction.addAction(regenerateShields(selectedMech));
                sequenceAction.addAction(new DelayAction(0.5f));
                sequenceAction.addAction(reduceStabilityLevel(selectedMech, battleMap));
                sequenceAction.addAction(new UnlockAction(actionLock, ""));
                ((AbstractMech) selectedMech).addAction(sequenceAction);
            }
        }

    }

    private Action centerCameraOnNext(StageElementsStorage stageElementsStorage) {
        CenterCameraAction centerCameraAction = new CenterCameraAction(stageElementsStorage, actionLock);
        centerCameraAction.setStartPosition(stageElementsStorage.stage.getCamera().position.x, stage.getCamera().position.y);
        //centerCameraAction.setStartPosition(stageElementsStorage.stage.getCamera().position.x, stage.getCamera().position.y);
        centerCameraAction.setPosition(next.getKey().getX(), next.getKey().getY());
        centerCameraAction.setDuration(1);
        return centerCameraAction;
    }

    private Action reduceHeatLevel(Pilot pilot, Mech mech, BattleMap battleMap) {
        int reduceAmount = heatCalculator.calculateCooling(pilot, mech, battleMap);

        int heatBeforeReducing = mech.getHeatLevel();

        mech.setHeatLevel(Math.max(mech.getHeatLevel() - reduceAmount, 0));
        SequenceAction sequenceAction = new SequenceAction();

        sequenceAction.addAction(new IntAction(heatBeforeReducing, mech::getHeatLevel, 1f, hudMediator.getHudElementsFacade().getHeatValueLabel()));
        return sequenceAction;
    }

    private Action reduceStabilityLevel(Mech mech, BattleMap battleMap) {

        int stabilityBeforeIncrease = mech.getStability();

        mech.setStability(stabilityDecreaseCalculator.calculate(mech, battleMap));
        SequenceAction sequenceAction = new SequenceAction();
        sequenceAction.addAction(new IntAction(stabilityBeforeIncrease, mech::getStability, 1f, hudMediator.getHudElementsFacade().getStabilityValueLabel()));

        return sequenceAction;
    }

    private Action regenerateShields(Mech mech) {

        int shieldBeforeIncrease = mech.getShieldValue();

        mech.getAllComponents().forEach(c -> {
            if (Shield.class.isAssignableFrom(c.getClass())) {
                c.update();
            }
        });

        SequenceAction sequenceAction = new SequenceAction();
        sequenceAction.addAction(new IntAction(shieldBeforeIncrease, mech::getShieldValue, 1f, hudMediator.getHudElementsFacade().getShieldValueLabel()));
        return sequenceAction;
    }

    public boolean isNextPlayerControlled() {
        return this.next != null && this.team1.containsKey(this.next.getKey());
    }

}
