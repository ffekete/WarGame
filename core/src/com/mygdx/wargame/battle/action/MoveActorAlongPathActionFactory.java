package com.mygdx.wargame.battle.action;

import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.battle.map.Node;
import com.mygdx.wargame.battle.unit.action.AddDirectionMarkerAction;
import com.mygdx.wargame.battle.unit.action.ChangeDirectionAction;
import com.mygdx.wargame.battle.unit.action.RemoveDirectionMarkerAction;
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
        Node previous = paths.get(0);
        for (int i = 1; i < paths.getCount(); i++) {
            node = paths.get(i);

            if (mech.getMovementPoints() > 0) {
                mech.consumeMovementPoint(1);

                moveToAction.addAction(new ChangeDirectionAction(node.getX(), node.getY(), mech));
                moveToAction.addAction(new AddDirectionMarkerAction(node.getX(), node.getY(), mech, battleMap));
                moveToAction.addAction(new RemoveDirectionMarkerAction(previous.getX(), previous.getY(), battleMap));

                IsoMoveToAction moveToActionStep;

                moveToActionStep = new IsoMoveToAction(mech);
                moveToActionStep.setPosition(node.getX(), node.getY());
                moveToActionStep.setDuration(0.03f);

                moveToAction.addAction(moveToActionStep);
                previous = node;

            }
        }

        moveToAction.addAction(new RemovePathMarkersAction(battleMap));

        moveParallelAction.addAction(moveToAction);

        return moveParallelAction;
    }
}
