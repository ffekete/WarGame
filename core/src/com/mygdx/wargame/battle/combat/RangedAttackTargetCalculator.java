package com.mygdx.wargame.battle.combat;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.battle.map.Node;
import com.mygdx.wargame.battle.unit.AbstractWarrior;
import com.mygdx.wargame.battle.unit.Unit;
import com.mygdx.wargame.battle.unit.action.MovementAction;

import java.util.*;

public class RangedAttackTargetCalculator implements AttackCalculator {

    private BattleMap battleMap;
    private Stage stage;
    private ShapeRenderer shapeRenderer;

    public RangedAttackTargetCalculator(BattleMap battleMap, Stage stage, ShapeRenderer shapeRenderer) {
        this.battleMap = battleMap;
        this.stage = stage;
        this.shapeRenderer = shapeRenderer;
    }

    @Override
    public void calculate(Unit attacker, Unit defender) {
        // list all defenders
        Set<AbstractWarrior> attackers = new HashSet<>(attacker.getAll());

        Set<AbstractWarrior> defenders = new HashSet<>(defender.getAll());
        Set<AbstractWarrior> selected = new HashSet<>(); // this is empty

        long startTime = System.currentTimeMillis();
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
            Node s = new Node(0, at.getX(), at.getY(), 0, shapeRenderer);
            Node g = new Node(0, target.getX(), target.getY(), 0, shapeRenderer);
            List<Node> paths = battleMap.calculatePath(s, g, attacker.getRange());
            System.out.println("Found target : " + target.getName() + " for " + at.getName());
            System.out.println("Elapsed: " + (System.currentTimeMillis() - startTime));

            // save paths for later
            System.out.println("paths size: " + paths.size());

            paths.remove(paths.size() - 1);
            paths.remove(0);

            //paths.forEach(stage::addActor);
            battleMap.addPath(at, paths);
            at.addAction(new MovementAction(battleMap, at, stage));
        }


    }

    private AbstractWarrior findNextFree(AbstractWarrior attacker, Set<AbstractWarrior> defenders, boolean randomized) {
        TreeMap<Float, AbstractWarrior> distances = new TreeMap<>();
        defenders.forEach(
                d -> {
                    distances.put(distance(d.getX(), d.getY(), attacker.getX(), attacker.getY()), d);
                }
        );


            if (distances.size() > 0) {
                if(!randomized) {
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
