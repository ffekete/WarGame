package com.mygdx.wargame.battle.map;

import com.badlogic.gdx.ai.pfa.Heuristic;
import com.badlogic.gdx.math.Vector2;

public class NodeHeuristic implements Heuristic<Node> {

    @Override
    public float estimate(Node fromNode, Node toNode) {
        return Vector2.dst(fromNode.getX(), fromNode.getY(), toNode.getX(), toNode.getY());
    }
}
