package com.mygdx.wargame.battle.unit.action;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.battle.map.Node;
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

    public CalculatePathToUnit(Set<AbstractWarrior> attackers, BattleMap battleMap, Set<AbstractWarrior> defenders, ShapeRenderer shapeRenderer, Stage stage) {
        this.attackers = attackers;
        this.battleMap = battleMap;
        this.defenders = defenders;
        this.shapeRenderer = shapeRenderer;
        this.stage = stage;
    }

    @Override
    public boolean act(float delta) {
        if(!calculated) {
            calculated = true;
            for (AbstractWarrior at : attackers) {

                // walk through attackers, find a free defender
                AbstractWarrior target = findNextFree(at, defenders, false);

                if (target == null) {
                    // if nothing left, find the closest selected
                    target = findNextFree(at, selected, true);
                } else {
                    selected.add(target); // select target
                    defenders.remove(target);
                }

                // walk to the defender
                //double[] p = battleMap.getPath(at).isEmpty() ? new double[]{at.getX(), at.getY()} : new double[]{battleMap.getPath(at).get(battleMap.getPath(at).size() - 1).getX(), battleMap.getPath(at).get(battleMap.getPath(at).size() - 1).getY()};
                double[] p = new double[]{at.getX(), at.getY()};

                Node s = new Node(0, (int) p[0], (int) p[1], 0, shapeRenderer);
                Node g = new Node(0, target.getX(), target.getY(), 0, shapeRenderer);
                List<Node> paths = battleMap.calculatePath(s, g);
                System.out.println("Found target : " + target.getName() + " for " + at.getName());

                // save paths for later
                System.out.println("paths size: " + paths.size());

                paths.remove(paths.size() - 1);
                paths.remove(0);

                //paths.forEach(stage::addActor);
                battleMap.addPath(at, paths);

                at.addAction(new MovementAction(battleMap, at, stage));
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
