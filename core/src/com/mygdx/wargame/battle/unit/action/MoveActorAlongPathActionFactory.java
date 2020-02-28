package com.mygdx.wargame.battle.unit.action;

import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.assets.AssetManager;
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
import com.mygdx.wargame.decor.Birds;
import com.mygdx.wargame.mech.AbstractMech;
import com.mygdx.wargame.util.MapUtils;
import com.mygdx.wargame.util.MathUtils;

import java.util.Random;

public class MoveActorAlongPathActionFactory {

    private StageElementsStorage stageElementsStorage;
    private MovementMarkerFactory movementMarkerFactory;
    private MapUtils mapUtils = new MapUtils();
    private AssetManager assetManager;

    public MoveActorAlongPathActionFactory(StageElementsStorage stageElementsStorage, MovementMarkerFactory movementMarkerFactory, AssetManager assetManager) {
        this.stageElementsStorage = stageElementsStorage;
        this.movementMarkerFactory = movementMarkerFactory;
        this.assetManager = assetManager;
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

                ParallelAction moveAndShakeTreesAction = new ParallelAction();

                MoveToAction moveToActionStep = new MoveToAction();
                moveToActionStep.setPosition(node.getX(), node.getY());
                moveToActionStep.setDuration(1.1f);

                float nodex = node.getX();
                float nodey = node.getY();

                mapUtils.nrOfTreesOnTile(stageElementsStorage, node.getX(), node.getY()).forEach(tree-> {
                    moveAndShakeTreesAction.addAction(new ShakeAction(1.1f, tree));
                    if(new Random().nextInt(100) == 0)
                        moveAndShakeTreesAction.addAction(new AddActorAction(stageElementsStorage.airLevel, new Birds(assetManager,nodex, nodey)));
                });

                moveAndShakeTreesAction.addAction(moveToActionStep);

                moveToAction.addAction(moveAndShakeTreesAction);

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
