package com.mygdx.wargame.battle.unit.action;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Bezier;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RemoveActorAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.mygdx.wargame.battle.bullet.MissileSmoke;
import com.mygdx.wargame.battle.map.render.Renderers;
import com.mygdx.wargame.battle.screen.AssetManagerLoaderV2;
import com.mygdx.wargame.battle.screen.StageElementsStorage;

public class MoveActorByBezierLine extends TemporalAction {

    private Bezier<Vector2> bezier;
    private float angle;
    private float frequency = 0f;

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

        MissileSmoke missileSmoke = new MissileSmoke(AssetManagerLoaderV2.assetManager);
        missileSmoke.setPosition(target.getX(), target.getY());

        frequency += Gdx.graphics.getDeltaTime();

        if(frequency >= 0.025f) {
            SequenceAction sequenceAction = new SequenceAction();
            sequenceAction.addAction(new AddActorAction(Renderers.isometricTiledMapRendererWithSprites, missileSmoke));
            sequenceAction.addAction(Actions.delay(0.3f));
            sequenceAction.addAction(new RemoveCustomActorAction(Renderers.isometricTiledMapRendererWithSprites, missileSmoke, null));
            StageElementsStorage.stage.addAction(sequenceAction);
            frequency = 0f;
        }

        getTarget().setPosition(out.x, out.y);

        //        if (rotate) {
            bezier.derivativeAt(out, percent);
            getTarget().setRotation(out.angle());
//        }

        //getTarget().setRotation(angle);
    }
}
