package com.mygdx.wargame.battle.combat;

import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.battle.map.Node;
import com.mygdx.wargame.battle.map.NodeGraph;
import com.mygdx.wargame.battle.unit.AbstractWarrior;
import com.mygdx.wargame.battle.unit.action.MovementAction;

public class MeleeAttackTargetCalculator implements AttackCalculator {

    private BattleMap battleMap;
    private Stage stage;
    private NodeGraph nodeGraph;

    public MeleeAttackTargetCalculator(BattleMap battleMap, Stage stage, NodeGraph nodeGraph) {
        this.battleMap = battleMap;
        this.stage = stage;
        this.nodeGraph = nodeGraph;
    }

    @Override
    public void calculate(AbstractWarrior attacker, AbstractWarrior defender) {

        Node start = nodeGraph.getNodeWeb()[(int) attacker.getX()][(int) attacker.getY()];
        Node end = nodeGraph.getNodeWeb()[(int) defender.getX()][(int) defender.getY()];
        GraphPath<Node> paths = battleMap.calculatePath(start, end, 50);
        battleMap.addPath(attacker, paths);
        attacker.addAction(new MovementAction(battleMap, attacker, stage));
    }
}
