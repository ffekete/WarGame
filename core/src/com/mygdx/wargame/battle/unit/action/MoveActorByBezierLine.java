package com.mygdx.wargame.battle.unit.action;

import com.badlogic.gdx.math.Bezier;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;

public class MoveActorByBezierLine extends TemporalAction {

    private Bezier<Vector2> bezier;
    private float angle;

    public MoveActorByBezierLine(float sx, float sy, float ex, float ey, float ox, float oy, float angle) {

        Vector2[] points = new Vector2[3];

        points[1] = new Vector2(sx - ox, sy  - oy);

        points[0] = new Vector2(sx, sy);
        points[2] = new Vector2(ex, ey);

        this.angle = angle;

        bezier = new Bezier<>(points);
    }

    @Override
    protected void update(float percent) {

        Vector2 out = new Vector2();


        bezier.valueAt(out, percent);
        getTarget().setPosition(out.x, out.y);
//
//        if (rotate) {
//            bezier.derivativeAt(out, percent);
//            getTarget().setRotation(out.angle());
//        }
        getTarget().setRotation(angle);
    }
}
