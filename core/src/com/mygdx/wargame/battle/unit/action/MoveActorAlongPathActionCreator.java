package com.mygdx.wargame.battle.unit.action;

import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.mygdx.wargame.battle.map.Node;
import com.mygdx.wargame.battle.unit.State;
import com.mygdx.wargame.mech.AbstractMech;
import com.mygdx.wargame.util.MathUtils;

public class MoveActorAlongPathActionCreator {

    private GraphPath<Node> paths;
    private AbstractMech attacker;
    private int range;

    public MoveActorAlongPathActionCreator(GraphPath<Node> paths, AbstractMech attacker, int range) {
        this.paths = paths;
        this.attacker = attacker;
        this.range = range;
    }

    public SequenceAction act() {
        SequenceAction moveToAction = new SequenceAction();
        moveToAction.reset();
        moveToAction.addAction(new SetStateAction(attacker, State.Walk));
        int i = 0;
        Node last = paths.get(paths.getCount() -1);
        for (Node node : paths) {
            if (attacker.getMovementPoints() > 0 && i != 0 && MathUtils.getDistance(node.getX(), node.getY(), last.getX(), last.getY()) >= range) {
                attacker.consumeMovementPoint(1);
                moveToAction.addAction(new ChangeDirectionAction(node.getX(), node.getY(), attacker));
                MoveToAction moveToActionStep = new MoveToAction();
                moveToActionStep.setPosition(node.getX(), node.getY());
                moveToActionStep.setDuration(1.0f);
                moveToAction.addAction(moveToActionStep);
            }
            i++;
        }
        moveToAction.addAction(new SetStateAction(attacker, State.Idle));
        return moveToAction;
    }
}
