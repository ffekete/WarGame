package com.mygdx.wargame.battle.combat;

import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.battle.map.Node;
import com.mygdx.wargame.battle.map.NodeGraph;
import com.mygdx.wargame.battle.unit.AbstractMech;
import com.mygdx.wargame.battle.unit.action.MoveAndAttackAction;
import com.mygdx.wargame.battle.unit.action.MovementAction;

public class RangedAttackTargetCalculator implements AttackCalculator {

    private BattleMap battleMap;
    private Stage stage;
    private NodeGraph nodeGraph;

    public RangedAttackTargetCalculator(BattleMap battleMap, Stage stage, NodeGraph nodeGraph) {
        this.battleMap = battleMap;
        this.stage = stage;
        this.nodeGraph = nodeGraph;
    }

    @Override
    public void calculate(AbstractMech attacker, AbstractMech defender) {

        if (attacker != null && defender != null) {
            Node start = nodeGraph.getNodeWeb()[(int) attacker.getX()][(int) attacker.getY()];
            Node end = nodeGraph.getNodeWeb()[(int) defender.getX()][(int) defender.getY()];

            // reconnect so that attacker can move
            battleMap.getNodeGraphLv1().reconnectCities(battleMap.getNodeGraphLv1().getNodeWeb()[(int)attacker.getX()][(int)attacker.getY()]);

            GraphPath<Node> paths = battleMap.calculatePath(start, end, 50);
            battleMap.addPath(attacker, paths);
            attacker.addAction(new MoveAndAttackAction(battleMap, attacker, defender));
        }
    }
}
