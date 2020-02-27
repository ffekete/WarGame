package com.mygdx.wargame.battle.unit.action;

import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;

public class MoveActorByBezierLine extends TemporalAction {

    private CatmullRomSpline<Vector2> myCatmull;

    public MoveActorByBezierLine(float sx, float sy, float ex, float ey) {
        float xPts;
        float yPts;
        Vector2[] points = new Vector2[4];

        points[0] = new Vector2(sx, sy - 10);
        points[3] = new Vector2(ex, ey - 10);

        points[1] = new Vector2(sx, sy);
        points[2] = new Vector2(ex, ey);


        myCatmull = new CatmullRomSpline<Vector2>(points, false);
    }
//
//    @Override
//    public boolean act(float delta) {
//        Vector2 v = new Vector2();
//        path1.valueAt(v, actualPoint);
//        actualPoint++;
//        this.getActor().setPosition(v.x, v.y);
//
//        return false;
//    }

    @Override
    protected void update(float percent) {

        Vector2 out = new Vector2();


        myCatmull.valueAt(out, percent < 1 ? percent : 0.99f);
        getActor().setPosition(out.x, out.y);
        //System.out.println(" Move to: "  + out.x + " " + out.y);
        myCatmull.derivativeAt(out, percent);
        getActor().setRotation(out.angle());
    }

}
