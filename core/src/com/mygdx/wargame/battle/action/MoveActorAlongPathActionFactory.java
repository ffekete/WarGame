package com.mygdx.wargame.battle.action;

import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.battle.map.Node;
import com.mygdx.wargame.common.mech.AbstractMech;

public class MoveActorAlongPathActionFactory {

    private BattleMap battleMap;

    public MoveActorAlongPathActionFactory(BattleMap battleMap) {
        this.battleMap = battleMap;
    }

    public ParallelAction getMovementAction(GraphPath<Node> paths, AbstractMech mech) {

        ParallelAction moveParallelAction = new ParallelAction();
        SequenceAction moveToAction = new SequenceAction();

        Node node;
        for (int i = 1; i < paths.getCount(); i++) {
            node = paths.get(i);


            if (mech.getMovementPoints() > 0) {
                mech.consumeMovementPoint(1);

                IsoMoveToAction moveToActionStep;

                moveToActionStep = new IsoMoveToAction(mech);
                moveToActionStep.setPosition(node.getX(), node.getY());
                moveToActionStep.setDuration(0.01f);

                moveToAction.addAction(moveToActionStep);
            }
        }

        moveToAction.addAction(new RemovePathMarkersAction(battleMap));

        moveParallelAction.addAction(moveToAction);

        return moveParallelAction;
    }
}
