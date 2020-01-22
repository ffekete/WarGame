package com.mygdx.wargame.battle.unit.action;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.RotateToAction;
import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.battle.map.Node;
import com.mygdx.wargame.battle.unit.AbstractWarrior;
import com.mygdx.wargame.battle.unit.Unit;
import com.mygdx.wargame.util.MathUtils;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Comparator;
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

    public RotateUnit(Unit unit, double[] target, BattleMap battleMap, ShapeRenderer shapeRenderer, Stage stage) {
        this.unit = unit;
        this.battleMap = battleMap;
        this.shapeRenderer = shapeRenderer;
        this.stage = stage;
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
                    battleMap.addPath(man, battleMap.calculatePath(new Node(0, (int) man.getX(), (int) man.getY(), 0, shapeRenderer),
                            new Node(0, newCoord.get(man).x, (int) newCoord.get(man).y, 0, shapeRenderer), 1));

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