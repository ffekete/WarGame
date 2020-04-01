package com.mygdx.wargame.battle.rules.facade;

import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.DelayAction;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.mygdx.wargame.battle.action.*;
import com.mygdx.wargame.battle.lock.ActionLock;
import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.battle.map.Node;
import com.mygdx.wargame.battle.map.render.IsometricTiledMapRendererWithSprites;
import com.mygdx.wargame.battle.rules.calculator.HeatCalculator;
import com.mygdx.wargame.battle.rules.calculator.MovementSpeedCalculator;
import com.mygdx.wargame.battle.rules.calculator.RangeCalculator;
import com.mygdx.wargame.battle.rules.calculator.StabilityDecreaseCalculator;
import com.mygdx.wargame.battle.rules.facade.target.Target;
import com.mygdx.wargame.battle.rules.facade.target.TargetingFacade;
import com.mygdx.wargame.battle.screen.AssetManagerLoaderV2;
import com.mygdx.wargame.battle.screen.StageElementsStorage;
import com.mygdx.wargame.battle.screen.ui.HUDMediator;
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

public class

TurnProcessingFacade {

    private Map<AbstractMech, Pilot> allSorted = new TreeMap<>();
    private ActionLock actionLock;
    private AttackFacade attackFacade;
    private TargetingFacade targetingFacade;
    private MovementSpeedCalculator movementSpeedCalculator;
    private Map<AbstractMech, Pilot> team1;
    private Map<AbstractMech, Pilot> team2;
    private Iterator<Map.Entry<AbstractMech, Pilot>> iterator;
    private Map.Entry<AbstractMech, Pilot> next = null;
    private RangeCalculator rangeCalculator;
    private StageElementsStorage stageElementsStorage;
    private MoveActorAlongPathActionFactory moveActorAlongPathActionFactory;
    private HeatCalculator heatCalculator;
    private WeaponSelectionOptimizer weaponSelectionOptimizer;
    private StabilityDecreaseCalculator stabilityDecreaseCalculator;
    private HUDMediator hudMediator;
    private BattleMap battleMap;
    private AssetManagerLoaderV2 assetManagerLoaderV2;
    private IsometricTiledMapRendererWithSprites isometricTiledMapRendererWithSprites;
    private WeaponRangeMarkerUpdater weaponRangeMarkerUpdater;
    private DeploymentFacade deploymentFacade;
    private int turnCounter = 1;

    public TurnProcessingFacade(ActionLock actionLock, AttackFacade attackFacade, TargetingFacade targetingFacade, MovementSpeedCalculator movementSpeedCalculator,
                                Map<AbstractMech, Pilot> team1, Map<AbstractMech, Pilot> team2, RangeCalculator rangeCalculator, StageElementsStorage stageElementsStorage, HeatCalculator heatCalculator, StabilityDecreaseCalculator stabilityDecreaseCalculator, HUDMediator hudMediator, BattleMap battleMap, AssetManagerLoaderV2 assetManagerLoaderV2, IsometricTiledMapRendererWithSprites isometricTiledMapRendererWithSprites, DeploymentFacade deploymentFacade) {
        this.actionLock = actionLock;
        this.attackFacade = attackFacade;
        this.targetingFacade = targetingFacade;
        this.movementSpeedCalculator = movementSpeedCalculator;
        this.hudMediator = hudMediator;

        this.team1 = team1;
        this.team2 = team2;
        this.rangeCalculator = rangeCalculator;

        this.stageElementsStorage = stageElementsStorage;
        this.heatCalculator = heatCalculator;
        this.stabilityDecreaseCalculator = stabilityDecreaseCalculator;
        this.battleMap = battleMap;
        this.assetManagerLoaderV2 = assetManagerLoaderV2;
        this.isometricTiledMapRendererWithSprites = isometricTiledMapRendererWithSprites;
        this.deploymentFacade = deploymentFacade;

        this.team1.forEach((key, value) -> allSorted.put((AbstractMech) key, value));
        this.team2.forEach((key, value) -> allSorted.put((AbstractMech) key, value));

        iterator = allSorted.entrySet().iterator();

        this.moveActorAlongPathActionFactory = new MoveActorAlongPathActionFactory(this.battleMap);

        this.weaponSelectionOptimizer = new WeaponSelectionOptimizer();

        this.weaponRangeMarkerUpdater = new WeaponRangeMarkerUpdater();
    }

    public Map.Entry<AbstractMech, Pilot> getNext() {
        return next;
    }

    public void process(BattleMap battleMap) {
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

            turnCounter++;
            ParallelAction parallelAction = new ParallelAction();

            SequenceAction sequenceAction = new SequenceAction();
            deploymentFacade.addMovingLabelShadow(sequenceAction, "TURN " + turnCounter);
            SequenceAction sequenceAction1 = new SequenceAction();
            deploymentFacade.addMovingLabel(sequenceAction1, "TURN " + turnCounter);
            parallelAction.addAction(sequenceAction);
            parallelAction.addAction(sequenceAction1);

            stageElementsStorage.hudStage.addAction(parallelAction);

            next = iterator.next();
            //centerCameraOnNext(stage);
        } else {
            next = iterator.next();
            //centerCameraOnNext(stage);
        }

        if (team2.keySet().stream().noneMatch(Mech::isActive)) {

        } else if (team1.keySet().stream().noneMatch(Mech::isActive)) {

        } else {

            AbstractMech selectedMech = next.getKey();
            Pilot selectedPilot = next.getValue();

            hudMediator.getHudElementsFacade().populateSidePanel();

            battleMap.clearRangeMarkers();
            battleMap.clearMovementMarkers();

            if (!selectedMech.isActive()) {
                // skip, if deactivated
                if (iterator.hasNext()) {
                    next = iterator.next();
                }
            } else if (team2.containsKey(selectedMech)) {

                hudMediator.getHudElementsFacade().populateSidePanel();

                weaponSelectionOptimizer.doIt(selectedMech);

                SequenceAction sequenceAction = new SequenceAction();

                sequenceAction.reset();

                // lock all actions
                sequenceAction.addAction(new LockAction(actionLock));

                // reconnect graph so that attacker can move
                //battleMap.getNodeGraph().reconnectCities(battleMap.getNodeGraph().getNodeWeb()[(int) selectedMech.getX()][(int) selectedMech.getY()]);

                // calculate movement points
                int movementPoints = movementSpeedCalculator.calculate(selectedPilot, selectedMech, battleMap);
                selectedMech.resetMovementPoints(movementPoints);

                sequenceAction.addAction(new ClearRangeMarkersAction(battleMap));
                sequenceAction.addAction(new ClearMovementMarkersAction(battleMap));
                sequenceAction.addAction(new RemoveSelectionMarkerAction(isometricTiledMapRendererWithSprites));
                sequenceAction.addAction(new CreateSelectionMarkerAction(isometricTiledMapRendererWithSprites, assetManagerLoaderV2, selectedMech));

                sequenceAction.addAction(reduceHeatLevel(selectedPilot, selectedMech, battleMap));
                sequenceAction.addAction(new DelayAction(0.5f));
                sequenceAction.addAction(regenerateShields(selectedMech));
                sequenceAction.addAction(new DelayAction(0.5f));
                sequenceAction.addAction(reduceStabilityLevel(selectedMech, battleMap));
                sequenceAction.addAction(new DelayAction(0.5f));

                // find target
                Optional<Target> target = targetingFacade.findTarget(selectedPilot, selectedMech, team1, battleMap);

                int minRange = rangeCalculator.calculateAllWeaponsRange(selectedPilot, selectedMech);

                // move if target too far away
                if (target.isPresent()) {
                    if (target.get().getTargetNode() != null) {
                        System.out.println("Found target node " + target.get().getTargetNode().isImpassable());

                        // calculate path
                        GraphPath<Node> paths = battleMap.calculatePath(selectedMech, battleMap.getNodeGraph().getNodeWeb()[(int) selectedMech.getX()][(int) selectedMech.getY()],
                                battleMap.getNodeGraph().getNodeWeb()[(int) target.get().getTargetNode().getX()][(int) target.get().getTargetNode().getY()]);

                        sequenceAction.addAction(new RemoveSelectionMarkerAction(isometricTiledMapRendererWithSprites));
                        sequenceAction.addAction(moveActorAlongPathActionFactory.getMovementAction(paths, (AbstractMech) selectedMech));
                        sequenceAction.addAction(new CreateSelectionMarkerAction(isometricTiledMapRendererWithSprites, assetManagerLoaderV2, selectedMech));

                    } else if (MathUtils.getDistance(selectedMech.getX(), selectedMech.getY(), target.get().getMech().getX(), target.get().getMech().getY()) > minRange) {
                        // reconnect graph so that attacker can move
                        //battleMap.getNodeGraph().reconnectCities(battleMap.getNodeGraph().getNodeWeb()[(int) selectedMech.getX()][(int) selectedMech.getY()]);

                        // calculate path
                        GraphPath<Node> paths = battleMap.calculatePath(selectedMech, battleMap.getNodeGraph().getNodeWeb()[(int) selectedMech.getX()][(int) selectedMech.getY()],
                                battleMap.getNodeGraph().getNodeWeb()[(int) target.get().getMech().getX()][(int) target.get().getMech().getY()]);

                        sequenceAction.addAction(moveActorAlongPathActionFactory.getMovementAction(paths, (AbstractMech) selectedMech));

                    } else {
                        // obstacle again, no movement
                        //battleMap.setTemporaryObstacle(selectedMech.getX(), selectedMech.getY());
                    }


                    if (target.get().getMech() != null) {

                        // then attack
                        ParallelAction attackActions = new ParallelAction();
                        attackActions.addAction(new ChangeDirectionAction(target.get().getMech().getX(), target.get().getMech().getY(), selectedMech));
                        attackActions.addAction(new RemoveDirectionMarkerAction(selectedMech.getX(), selectedMech.getY(), battleMap));
                        attackActions.addAction(new AddDirectionMarkerAction(selectedMech, battleMap));
                        if(!selectedMech.isRangedAttack()) {
                            attackActions.addAction(new AttackAnimationAction(selectedMech, target.get().getMech()));
                        } else {
                            attackActions.addAction(new BulletAnimationAction(selectedMech, target.get().getMech(), assetManagerLoaderV2.getAssetManager(), actionLock, minRange, stageElementsStorage, isometricTiledMapRendererWithSprites, battleMap, sequenceAction));
                        }
                        AttackAction attackAction = new AttackAction(attackFacade, selectedMech, selectedPilot, target.get().getMech(), target.get().getPilot(), battleMap, minRange, null);
                        sequenceAction.addAction(attackActions);
                        sequenceAction.addAction(attackAction);
                    }
                    //sequenceAction.addAction(new UnlockAction(actionLock, "Eof AI attack"));

                    sequenceAction.addAction(new DelayAction(1f));
                    sequenceAction.addAction(new RemoveSelectionMarkerAction(isometricTiledMapRendererWithSprites));
                    sequenceAction.addAction(new UnlockAction(actionLock, "eof enemy turn"));
                    stageElementsStorage.stage.addAction(sequenceAction);
                }

            } else {

                hudMediator.getHudElementsFacade().populateSidePanel();

                // wait for "next" button press
                int movementPoints = movementSpeedCalculator.calculate(selectedPilot, selectedMech, battleMap);
                selectedMech.resetMovementPoints(movementPoints);

                SequenceAction sequenceAction = new SequenceAction();
                sequenceAction.addAction(new LockAction(actionLock));
                sequenceAction.addAction(new ClearRangeMarkersAction(battleMap));
                sequenceAction.addAction(new ClearMovementMarkersAction(battleMap));
                sequenceAction.addAction(new RemoveSelectionMarkerAction(isometricTiledMapRendererWithSprites));
                sequenceAction.addAction(new CreateSelectionMarkerAction(isometricTiledMapRendererWithSprites, assetManagerLoaderV2, selectedMech));
                sequenceAction.addAction(new AddMovementMarkersAction(battleMap, selectedMech));
                sequenceAction.addAction(new AddRangeMarkersAction(battleMap, selectedMech, selectedPilot));
                sequenceAction.addAction(reduceHeatLevel(selectedPilot, selectedMech, battleMap));
                sequenceAction.addAction(new DelayAction(0.5f));
                sequenceAction.addAction(regenerateShields(selectedMech));
                sequenceAction.addAction(new DelayAction(0.5f));
                sequenceAction.addAction(reduceStabilityLevel(selectedMech, battleMap));
                sequenceAction.addAction(new DelayAction(0.5f));
                sequenceAction.addAction(new UnlockAction(actionLock, "eof player warmup"));
                stageElementsStorage.stage.addAction(sequenceAction);
            }
        }
    }

    private Action reduceHeatLevel(Pilot pilot, Mech mech, BattleMap battleMap) {
        int reduceAmount = heatCalculator.calculateCooling(pilot, mech, battleMap);

        int heatBeforeReducing = mech.getHeatLevel();

        mech.setHeatLevel(Math.max(mech.getHeatLevel() - reduceAmount, 0));
        SequenceAction sequenceAction = new SequenceAction();

        sequenceAction.addAction(new IntAction(heatBeforeReducing, mech::getHeatLevel, 1f, hudMediator.getHudElementsFacade().getHeatImage().getLabel(), "heat: "));
        return sequenceAction;
    }

    private Action reduceStabilityLevel(Mech mech, BattleMap battleMap) {

        int stabilityBeforeIncrease = mech.getStability();

        mech.setStability(stabilityDecreaseCalculator.calculate(mech, battleMap));
        SequenceAction sequenceAction = new SequenceAction();
        sequenceAction.addAction(new IntAction(stabilityBeforeIncrease, mech::getStability, 1f, hudMediator.getHudElementsFacade().getStabilityImage().getLabel(), "stability: "));

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
        sequenceAction.addAction(new IntAction(shieldBeforeIncrease, mech::getShieldValue, 1f, hudMediator.getHudElementsFacade().getShieldImage().getLabel(), "shield: "));
        return sequenceAction;
    }

    public boolean isNextPlayerControlled() {
        return this.next != null && this.team1.containsKey(this.next.getKey());
    }

    public AttackFacade getAttackFacade() {
        return attackFacade;
    }

    public BattleMap getBattleMap() {
        return battleMap;
    }
}
