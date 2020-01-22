package com.mygdx.wargame.battle.unit.action;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.battle.map.Node;
import com.mygdx.wargame.battle.map.NodeGraph;
import com.mygdx.wargame.battle.unit.AbstractWarrior;
import com.mygdx.wargame.battle.unit.Unit;
import com.mygdx.wargame.util.MathUtils;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.HashMap;
import java.util.Map;

public class RotateUnit extends Action {

    private Unit unit;
    private boolean calculated = false;
    private Map<AbstractWarrior, Point> newCoord = new HashMap<>();
    private BattleMap battleMap;
    private ShapeRenderer shapeRenderer;
    private Stage stage;
    private float angle;
    private NodeGraph nodeGraph;

    public RotateUnit(Unit unit, double[] target, BattleMap battleMap, ShapeRenderer shapeRenderer, Stage stage, NodeGraph nodeGraph) {
        this.unit = unit;
        this.battleMap = battleMap;
        this.shapeRenderer = shapeRenderer;
        this.stage = stage;
        this.nodeGraph = nodeGraph;
        double[] centre = unit.getCenter();
        this.angle = MathUtils.getAngle(centre, target);
    }

    @Override
    public boolean act(float delta) {

        // calculate new path
        if (!calculated) {

            double[] centre = unit.getCenter();

            for (int i = 0; i < unit.getLayout().length; i++) {
                for (int j = 0; j < unit.getLayout()[0].length; j++) {

                    // get warrior
                    AbstractWarrior man = unit.getLayout()[i][j];

                    // find new position
                    rotateToNewPosition(centre, man, new double[]{man.getX(), man.getY()});

                    // calculate path to new rotated position
                    battleMap.addPath(man, battleMap.calculatePath(nodeGraph.getNodeWeb()[(int)man.getX()][(int)man.getY()],
                            nodeGraph.getNodeWeb()[(int)newCoord.get(man).getX()][(int)newCoord.get(man).getY()], 0));

                    man.addAction(new MovementAction(battleMap, man, stage));
                }
            }

            calculated = true;
        }

        return unit.getAll().stream().allMatch(u -> u.getActions().isEmpty());
    }

    private void rotateToNewPosition(double[] centre, AbstractWarrior man, double[] pt) {
        AffineTransform.getRotateInstance(Math.toRadians(angle), centre[0], centre[1])
                .transform(pt, 0, pt, 0, 1); // specifying to use this double[] to hold coords

        newCoord.put(man, new Point((int) pt[0], (int) pt[1]));
    }


}