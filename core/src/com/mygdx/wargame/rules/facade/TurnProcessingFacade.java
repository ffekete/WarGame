package com.mygdx.wargame.rules.facade;

import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.mygdx.wargame.battle.action.CenterCameraAction;
import com.mygdx.wargame.battle.lock.ActionLock;
import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.battle.map.Node;
import com.mygdx.wargame.battle.screen.StageStorage;
import com.mygdx.wargame.battle.unit.action.AttackAction;
import com.mygdx.wargame.battle.unit.action.AttackAnimationAction;
import com.mygdx.wargame.battle.unit.action.BulletAnimationAction;
import com.mygdx.wargame.battle.unit.action.LockAction;
import com.mygdx.wargame.battle.unit.action.MoveIntoRangeAction;
import com.mygdx.wargame.mech.Mech;
import com.mygdx.wargame.pilot.Pilot;
import com.mygdx.wargame.rules.calculator.MovementSpeedCalculator;
import com.mygdx.wargame.rules.calculator.RangeCalculator;
import com.mygdx.wargame.rules.facade.target.Target;
import com.mygdx.wargame.rules.facade.target.TargetingFacade;
import com.mygdx.wargame.util.MathUtils;

import java.util.Iterator;
import java.util.Map;
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
    private StageStorage stageStorage;

    public TurnProcessingFacade(ActionLock actionLock, AttackFacade attackFacade, TargetingFacade targetingFacade, MovementSpeedCalculator movementSpeedCalculator,
                                Map<Mech, Pilot> team1, Map<Mech, Pilot> team2, RangeCalculator rangeCalculator, Stage stage, Stage hudStage, AssetManager assetManager, StageStorage stageStorage) {
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
        this.stageStorage = stageStorage;

        this.team1.forEach((key, value) -> allSorted.put(key, value));
        this.team2.forEach((key, value) -> allSorted.put(key, value));

        iterator = allSorted.entrySet().iterator();
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

            centerCameraOnNext(stage);
        } else {
            next = iterator.next();
            centerCameraOnNext(stage);
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

            // lock all actions
            sequenceAction.addAction(new LockAction(actionLock));

            // find target
            Target target = targetingFacade.findTarget(selectedPilot, selectedMech, team1, battleMap);

            // calculate movement points
            int movementPoints = movementSpeedCalculator.calculate(selectedPilot, selectedMech, battleMap);
            selectedMech.resetMovementPoints(movementPoints);

            int minRange = rangeCalculator.calculateAllWeaponsRange(selectedPilot, target.getMech());

            // move if target too far away
            if (MathUtils.getDistance(selectedMech.getX(), selectedMech.getY(), target.getMech().getX(), target.getMech().getY()) > minRange) {

                // reconnect graph so that attacker can move
                battleMap.getNodeGraphLv1().reconnectCities(battleMap.getNodeGraphLv1().getNodeWeb()[(int) selectedMech.getX()][(int) selectedMech.getY()]);

                // calculate path
                GraphPath<Node> paths = battleMap.calculatePath(battleMap.getNodeGraphLv1().getNodeWeb()[(int) selectedMech.getX()][(int) selectedMech.getY()],
                        battleMap.getNodeGraphLv1().getNodeWeb()[(int) target.getMech().getX()][(int) target.getMech().getY()]);

                battleMap.addPath(selectedMech, paths);

                sequenceAction.addAction(new MoveIntoRangeAction(battleMap, selectedMech, selectedPilot, target.getMech(), rangeCalculator));
            }

            // then attack
            sequenceAction.addAction(new AttackAnimationAction(selectedMech, target.getMech(), minRange));
            sequenceAction.addAction(new BulletAnimationAction(selectedMech, target.getMech(), stage, hudStage, assetManager, actionLock, minRange, stageStorage, battleMap));
            AttackAction attackAction = new AttackAction(attackFacade, selectedMech, selectedPilot, target.getMech(), target.getPilot(), battleMap, minRange);
            sequenceAction.addAction(attackAction);

            // unlock all actions
            //sequenceAction.addAction(new UnlockAction(actionLock));

            stage.addAction(sequenceAction);

        } else {
            // wait for "next" button press
            int movementPoints = movementSpeedCalculator.calculate(selectedPilot, selectedMech, battleMap);
            selectedMech.resetMovementPoints(movementPoints);
        }
    }

    private void centerCameraOnNext(Stage stage) {
        CenterCameraAction centerCameraAction = new CenterCameraAction(stage.getCamera());
        centerCameraAction.setStartPosition(stage.getCamera().position.x, stage.getCamera().position.y);
        centerCameraAction.setPosition(next.getKey().getX(), next.getKey().getY());
        centerCameraAction.setDuration(1);
        stage.addAction(centerCameraAction);
    }

    public boolean isNextPlayerControlled() {
        return this.next != null && this.team1.containsKey(this.next.getKey());
    }

}
