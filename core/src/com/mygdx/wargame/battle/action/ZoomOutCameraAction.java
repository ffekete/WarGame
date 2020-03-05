package com.mygdx.wargame.battle.action;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.mygdx.wargame.battle.screen.StageElementsStorage;
import com.mygdx.wargame.common.mech.Mech;

public class ZoomOutCameraAction extends TemporalAction {

    private StageElementsStorage stageElementsStorage;
    private Mech mech1, mech2;
    private OrthographicCamera camera;
    private boolean firstRun = true;

    float cx, cy;
    float startX, startY, endX, endY;

    float targetZoom;

    public ZoomOutCameraAction(StageElementsStorage stageElementsStorage, Mech mech1, Mech mech2, OrthographicCamera camera) {
        this.stageElementsStorage = stageElementsStorage;
        this.mech1 = mech1;
        this.mech2 = mech2;
        this.camera = camera;

        setDuration(1);


    }

    @Override
    protected void update(float percent) {

        if (firstRun) {
            startX = mech1.getX();
            startY = mech1.getY();
            endX = Math.abs(mech1.getX() + mech2.getY()) / 2f;
            endY = Math.abs(mech1.getY() + mech2.getY()) / 2f;
            firstRun = false;
        }
        float x, y;
        if (percent == 0) {
            x = startX;
            y = startY;
        } else if (percent == 1) {
            x = endX;
            y = endY;
        } else {
            x = startX + (endX - startX) * percent;
            y = startY + (endY - startY) * percent;
        }
        //target.setPosition(x, y);
        camera.position.x = x;
        camera.position.y = y;
    }
}
