package com.mygdx.wargame.battle.unit.action;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.wargame.decor.Smoke;

public class MoveSmokingActorByBezierLine extends MoveActorByBezierLine {

    private CatmullRomSpline<Vector2> myCatmull;
    private boolean roatate = false;
    private AssetManager assetManager;
    private int emitStep = 0;

    public MoveSmokingActorByBezierLine(float sx, float sy, float ex, float ey, boolean rotate, AssetManager assetManager) {
        super(sx, sy, ex, ey, rotate);
        float xPts;
        float yPts;
        Vector2[] points = new Vector2[4];

        points[0] = new Vector2(sx, sy - 10);
        points[3] = new Vector2(ex, ey - 10);

        points[1] = new Vector2(sx, sy);
        points[2] = new Vector2(ex, ey);

        this.roatate = rotate;

        this.assetManager = assetManager;


        myCatmull = new CatmullRomSpline<Vector2>(points, false);
    }

    @Override
    protected void update(float percent) {

        Vector2 out = new Vector2();

        myCatmull.valueAt(out, percent);

        emitStep += 1;

        if (emitStep == 3) {
            emitStep = 0;

            actor.addAction(new AddActorAction(actor.getStage(), new Smoke(assetManager, out.x, out.y)));
        }

        getActor().setPosition(out.x, out.y);

        if (roatate) {
            myCatmull.derivativeAt(out, percent);
            getActor().setRotation(out.angle());
        }
    }
}
