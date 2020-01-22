package com.mygdx.wargame.battle.unit.action;

import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.battle.map.Node;
import com.mygdx.wargame.battle.map.NodeGraph;
import com.mygdx.wargame.battle.unit.AbstractWarrior;

import java.util.*;

public class CalculatePathToUnit extends Action {

    private Set<AbstractWarrior> attackers;
    private BattleMap battleMap;
    private Set<AbstractWarrior> defenders;
    private Set<AbstractWarrior> selected = new HashSet<>(); // this is empty
    private ShapeRenderer shapeRenderer;
    private Stage stage;
    private boolean calculated = false;
    private NodeGraph nodeGraph;

    public CalculatePathToUnit(Set<AbstractWarrior> attackers, BattleMap battleMap, Set<AbstractWarrior> defenders, ShapeRenderer shapeRenderer, Stage stage, NodeGraph nodeGraph) {
        this.attackers = attackers;
        this.battleMap = battleMap;
        this.defenders = defenders;
        this.shapeRenderer = shapeRenderer;
        this.stage = stage;
        this.nodeGraph = nodeGraph;
    }

    @Override
    public boolean act(float delta) {
        if(!calculated) {
            calculated = true;
            for (AbstractWarrior at : attackers) {

                // cycle through attackers, find a free defender
                AbstractWarrior target = findNextFree(at, defenders, false);

                if (target == null) {
                    // if nothing left, find the closest selected
                    target = findNextFree(at, selected, true);
                } else {
                    selected.add(target); // select target
                    defenders.remove(target);
                }

                // walk to the defender
                double[] p = new double[]{at.getX(), at.getY()};

                Node s = nodeGraph.getNodeWeb()[(int)p[0]][(int)p[1]];//new Node((float) p[0],(float) p[1]);
                Node g = nodeGraph.getNodeWeb()[(int)target.getX()][(int)target.getY()];

                new Thread(() -> {
                    GraphPath<Node> paths = battleMap.calculatePath(s, g, 50);

                    // save paths
                    battleMap.addPath(at, paths);

                    at.addAction(new MovementAction(battleMap, at, stage));
                }).start();
            }
        }

        return attackers.stream().allMatch(u -> u.getActions().isEmpty());
    }

    private AbstractWarrior findNextFree(AbstractWarrior attacker, Set<AbstractWarrior> defenders,
                                         boolean randomized) {
        TreeMap<Float, AbstractWarrior> distances = new TreeMap<>();
        defenders.forEach(
                d -> {
                    distances.put(distance(d.getX(), d.getY(), attacker.getX(), attacker.getY()), d);
                }
        );


        if (distances.size() > 0) {
            if (!randomized) {
                return distances.get(distances.firstKey());
            } else {
                return distances.get(distances.keySet().toArray()[new Random().nextInt(distances.keySet().toArray().length)]);
            }
        }

        return null;
    }

    private static float distance(float x, float y, float x2, float y2) {
        float a = Math.abs(x2 - x);
        float b = Math.abs(y2 - y);
        return a * a + b * b;
    }

}
