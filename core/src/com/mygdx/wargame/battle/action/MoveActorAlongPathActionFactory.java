package com.mygdx.wargame.battle.action;

import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.mygdx.wargame.battle.map.Node;
import com.mygdx.wargame.common.mech.AbstractMech;

public class MoveActorAlongPathActionFactory {

    public MoveActorAlongPathActionFactory() {

    }

    public ParallelAction getMovementAction(GraphPath<Node> paths, AbstractMech mech) {

        ParallelAction moveAndFollowCamera = new ParallelAction();
        SequenceAction moveToAction = new SequenceAction();

        Node last = paths.get(paths.getCount() - 1);
        Node node = null;
        for (int i = 1; i < paths.getCount(); i++) {
            Node latest = paths.get(i - 1);
            node = paths.get(i);

            float nx = node.getX();
            float ny = node.getY();

            if (mech.getMovementPoints() > 0) {
                mech.consumeMovementPoint(1);

                IsoMoveToAction moveToActionStep;

                moveToActionStep = new IsoMoveToAction(mech);
                moveToActionStep.setPosition(node.getX(), node.getY());
                moveToActionStep.setDuration(1.1f);

                moveToAction.addAction(moveToActionStep);
            }
        }

        moveAndFollowCamera.addAction(moveToAction);

        return moveAndFollowCamera;
    }
}
