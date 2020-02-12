package com.mygdx.wargame.battle.unit.action;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Bezier;
import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.mygdx.wargame.config.Config;

import java.util.Random;

public class MoveActorByBezierLine extends TemporalAction {

    private CatmullRomSpline<Vector2> myCatmull;

    public MoveActorByBezierLine(float sx, float sy, float ex, float ey) {
        float xPts;
        float yPts;
        Vector2[] points = new Vector2[4];

        points[0] = new Vector2(sx, sy - 100);
        points[3] = new Vector2(ex, ey - 100);

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
        float speed = 0.15f;
        float current = 0;
        Vector2 out = new Vector2();
        /*render()*/

        current += Gdx.graphics.getDeltaTime() * speed;

        if (current >= 1)
            current -= 1;

        myCatmull.valueAt(out, percent < 1 ? percent : 0.99f);
        getActor().setPosition(out.x, out.y);
        myCatmull.derivativeAt(out, percent);
        getActor().setRotation(out.angle());
    }

}
