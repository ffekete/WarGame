package com.mygdx.wargame.battle.unit.action;

import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.mygdx.wargame.battle.action.FollowCameraAction;
import com.mygdx.wargame.battle.action.RemoveFollowCameraAction;
import com.mygdx.wargame.battle.action.SetTemporaryObstacleAction;
import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.battle.map.Node;
import com.mygdx.wargame.battle.map.movement.MovementMarkerFactory;
import com.mygdx.wargame.battle.screen.StageElementsStorage;
import com.mygdx.wargame.battle.unit.State;
import com.mygdx.wargame.mech.AbstractMech;
import com.mygdx.wargame.util.MathUtils;

public class MoveActorAlongPathActionFactory {

    private StageElementsStorage stageElementsStorage;
    private MovementMarkerFactory movementMarkerFactory;

    public MoveActorAlongPathActionFactory(StageElementsStorage stageElementsStorage, MovementMarkerFactory movementMarkerFactory) {
        this.stageElementsStorage = stageElementsStorage;
        this.movementMarkerFactory = movementMarkerFactory;
    }

    public ParallelAction act(GraphPath<Node> paths, AbstractMech attacker, int range, BattleMap battleMap) {

        ParallelAction moveAndFollowCamera = new ParallelAction();
        SequenceAction moveToAction = new SequenceAction();
        moveToAction.reset();

        moveToAction.addAction(new SetStateAction(attacker, State.Walk));
        moveToAction.addAction(new RemoveMovementMarkersAction(stageElementsStorage, movementMarkerFactory));
        Node last = paths.get(paths.getCount() - 1);
        Node node = null;
        for (int i = 1; i < paths.getCount(); i++) {
            Node latest = paths.get(i - 1);
            node = paths.get(i);

            float nx = node.getX();
            float ny = node.getY();

            if (attacker.getMovementPoints() > 0 && MathUtils.getDistance(latest.getX(), latest.getY(), last.getX(), last.getY()) > range) {
                attacker.consumeMovementPoint(1);

                moveToAction.addAction(new ChangeDirectionAction(node.getX(), node.getY(), attacker));
                MoveToAction moveToActionStep = new MoveToAction();
                moveToActionStep.setPosition(node.getX(), node.getY());
                moveToActionStep.setDuration(1.1f);
                moveToAction.addAction(moveToActionStep);

                moveToAction.addAction(new RemoveOneWayPointAction(stageElementsStorage, nx, ny));
            }
        }

        FollowCameraAction followCameraAction = new FollowCameraAction(stageElementsStorage, attacker);

        moveToAction.addAction(new SetStateAction(attacker, State.Idle));
        moveToAction.addAction(new RemoveFollowCameraAction(followCameraAction));

        if (node != null)
            moveToAction.addAction(new SetTemporaryObstacleAction(battleMap, (int) node.getX(), (int) node.getY()));

        moveAndFollowCamera.addAction(followCameraAction);
        moveAndFollowCamera.addAction(moveToAction);
        return moveAndFollowCamera;
    }
}
