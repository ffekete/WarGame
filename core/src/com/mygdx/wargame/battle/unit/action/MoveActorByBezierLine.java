package com.mygdx.wargame.battle.unit.action;

import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;

public class MoveActorByBezierLine extends TemporalAction {

    private CatmullRomSpline<Vector2> myCatmull;
    private boolean roatate = false;

    public MoveActorByBezierLine(float sx, float sy, float ex, float ey, boolean rotate) {

        Vector2[] points = new Vector2[4];

        points[0] = new Vector2(sx + 20, sy - 20);
        points[3] = new Vector2(ex + 20, ey - 20);

        points[1] = new Vector2(sx, sy);
        points[2] = new Vector2(ex, ey);

        this.roatate = rotate;


        myCatmull = new CatmullRomSpline<Vector2>(points, false);
    }

    @Override
    protected void update(float percent) {

        Vector2 out = new Vector2();


        myCatmull.valueAt(out, percent);
        getActor().setPosition(out.x, out.y);

        if (roatate) {
            myCatmull.derivativeAt(out, percent);
            getActor().setRotation(out.angle());
        }
    }
}
