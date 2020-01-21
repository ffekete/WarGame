package com.mygdx.wargame.battle.unit.action;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.RotateToAction;
import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.battle.map.Node;
import com.mygdx.wargame.battle.unit.AbstractWarrior;
import com.mygdx.wargame.battle.unit.Unit;

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
        this.angle = getAngle(centre, target);
    }

    @Override
    public boolean act(float delta) {

        // calculate new path
        if (!calculated) {

            double[] centre = unit.getCenter();

            for (int i = 0; i < unit.getLayout().length; i++) {
                for (int j = 0; j < unit.getLayout()[0].length; j++) {

                    AbstractWarrior man = unit.getLayout()[i][j];
                    double[] pt = {man.getX(), man.getY()};

                    // find new position
                    AffineTransform.getRotateInstance(Math.toRadians(angle), centre[0], centre[1])
                            .transform(pt, 0, pt, 0, 1); // specifying to use this double[] to hold coords
                    double newX = pt[0];
                    double newY = pt[1];

                    newCoord.put(man, new Point((int) newX, (int) newY));

                    // calculate path to new position
                    battleMap.addPath(man, battleMap.calculatePath(new Node(0, (int) man.getX(), (int) man.getY(), 0, shapeRenderer),
                            new Node(0, (int) newX, (int) newY, 0, shapeRenderer)));
                    man.addAction(new MovementAction(battleMap, man, stage));
                }
            }

            calculated = true;
        }

        return unit.getAll().stream().allMatch(u -> u.getActions().isEmpty());
    }

    public float getAngle(double[] from, double[] target) {
        float angle = (float) Math.toDegrees(Math.atan2(target[1] - from[1], target[0] - from[0]));

        if(angle < 0){
            angle += 360;
        }

        return -1 * angle;
    }
}