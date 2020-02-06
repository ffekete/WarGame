package com.mygdx.wargame.rules.facade;

import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.mygdx.wargame.battle.lock.ActionLock;
import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.battle.map.Node;
import com.mygdx.wargame.battle.unit.action.AttackAction;
import com.mygdx.wargame.battle.unit.action.LockAction;
import com.mygdx.wargame.battle.unit.action.MovementAction;
import com.mygdx.wargame.battle.unit.action.UnlockAction;
import com.mygdx.wargame.mech.Mech;
import com.mygdx.wargame.pilot.Pilot;
import com.mygdx.wargame.rules.calculator.MovementSpeedCalculator;
import com.mygdx.wargame.rules.facade.target.Target;
import com.mygdx.wargame.rules.facade.target.TargetingFacade;

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


    public TurnProcessingFacade(ActionLock actionLock, AttackFacade attackFacade, TargetingFacade targetingFacade, MovementSpeedCalculator movementSpeedCalculator,
                                Map<Mech, Pilot> team1, Map<Mech, Pilot> team2) {
        this.actionLock = actionLock;
        this.attackFacade = attackFacade;
        this.targetingFacade = targetingFacade;
        this.movementSpeedCalculator = movementSpeedCalculator;

        this.team1 = team1;
        this.team2 = team2;

        this.team1.forEach((key, value) -> allSorted.put(key, value));
        this.team2.forEach((key, value) -> allSorted.put(key, value));

        iterator = allSorted.entrySet().iterator();
    }

    public Map.Entry<Mech, Pilot> getNext() {
        return next;
    }

    public void process(BattleMap battleMap, Stage stage) {

        if(actionLock.isLocked()) {
            return;
        }

        if (!iterator.hasNext()) {
            iterator = allSorted.entrySet().iterator();
        }

        if (!iterator.hasNext())
            // todo end turn event!
            return;

        if(next != null && next.getKey().getMovementPoints() > 0) {
            return;
        }
        next = iterator.next();

        battleMap.removePath(next.getKey());

        if (!next.getKey().isActive()) {
            // skip
        } else if (team2.containsKey(next.getKey())) {

            SequenceAction sequenceAction = new SequenceAction();

            // lock all actions
            sequenceAction.addAction(new LockAction(actionLock));

            // find target
            Target target = targetingFacade.findTarget(next.getValue(), next.getKey(), team1, battleMap);

            // move
            int movementPoints = movementSpeedCalculator.calculate(next.getValue(), next.getKey(), battleMap);

            // reconnect graph so that attacker can move
            battleMap.getNodeGraphLv1().reconnectCities(battleMap.getNodeGraphLv1().getNodeWeb()[(int)next.getKey().getX()][(int)next.getKey().getY()]);

            GraphPath<Node> paths = battleMap.calculatePath(battleMap.getNodeGraphLv1().getNodeWeb()[(int)next.getKey().getX()][(int)next.getKey().getY()],
                    battleMap.getNodeGraphLv1().getNodeWeb()[(int)target.getMech().getX()][(int)target.getMech().getY()]);

            battleMap.addPath(next.getKey(), paths);

            next.getKey().resetMovementPoints(movementPoints);

            sequenceAction.addAction(new MovementAction(battleMap, next.getKey(), actionLock));

            // attack
            AttackAction attackAction = new AttackAction(attackFacade, next.getKey(), next.getValue(), target.getMech(), target.getPilot(), battleMap);
            sequenceAction.addAction(attackAction);

            // unlock all actions
            sequenceAction.addAction(new UnlockAction(actionLock));

            stage.addAction(sequenceAction);

        } else {
            // wait for "next" button press
            int movementPoints = movementSpeedCalculator.calculate(next.getValue(), next.getKey(), battleMap);
            next.getKey().resetMovementPoints(movementPoints);
        }


    }

}
