package com.mygdx.wargame.battle.unit.action;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.mygdx.wargame.battle.screen.StageElementsStorage;
import com.mygdx.wargame.battle.unit.State;
import com.mygdx.wargame.decor.Splash;
import com.mygdx.wargame.mech.AbstractMech;

public class JumpInAction extends MoveToAction {

    private MoveActorByBezierLine moveActorByBezierLine;
    private float sx, sy, ex, ey;
    private AbstractMech mech;
    private AssetManager assetManager;
    private StageElementsStorage stageElementsStorage;

    public JumpInAction(float sx, float sy, float ex, float ey, AbstractMech mech, AssetManager assetManager, StageElementsStorage stageElementsStorage) {
        this.sx = sx;
        this.sy = sy;
        this.ex = ex;
        this.ey = ey;
        this.mech = mech;
        this.stageElementsStorage = stageElementsStorage;
        setDuration(2f);
        moveActorByBezierLine = new MoveActorByBezierLine(sx, sy, ex, ey);
        moveActorByBezierLine.setActor(mech);
        moveActorByBezierLine.setDuration(2f);
        this.assetManager = assetManager;

    }

    @Override
    protected void update(float percent) {
        if(percent < 0.999) {
            mech.setState(State.Jump);
        } else {
            mech.addAction(new AddActorAction(stageElementsStorage.mechLevel, new Splash(assetManager, mech.getX(), mech.getY() - 0.3f)));
            mech.setState(State.Crouch);
        }
        moveActorByBezierLine.update(percent);
    }
}
