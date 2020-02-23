package com.mygdx.wargame.battle.action;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.mygdx.wargame.battle.screen.StageElementsStorage;
import com.mygdx.wargame.mech.Mech;

public class ZoomOutCameraAction extends TemporalAction {

    private StageElementsStorage stageElementsStorage;
    private Mech mech1, mech2;
    private OrthographicCamera camera;
    private boolean firstRun = true;

    float cx, cy;

    float targetZoom;

    public ZoomOutCameraAction(StageElementsStorage stageElementsStorage, Mech mech1, Mech mech2, OrthographicCamera camera) {
        this.stageElementsStorage = stageElementsStorage;
        this.mech1 = mech1;
        this.mech2 = mech2;
        this.camera = camera;

        setDuration(2);
    }

    @Override
    protected void update(float percent) {

        if(firstRun) {
            targetZoom = 1f;

            while (!(camera.position.x - camera.viewportWidth / 2f <= mech1.getX() && camera.position.x + camera.viewportWidth / 2f >= mech1.getX()
                    && camera.position.x - camera.viewportWidth / 2f <= mech2.getX() && camera.position.x + camera.viewportWidth / 2f>= mech2.getX() &&
                    camera.position.y - camera.viewportHeight / 2f <= mech1.getY() && camera.position.y + camera.viewportHeight / 2 >= mech1.getY()
                    && camera.position.y - camera.viewportHeight / 2f <= mech2.getY() && camera.position.y + camera.viewportHeight / 2f>= mech2.getY()))
                targetZoom += 0.5f;

            cx = Math.abs(mech1.getX() + mech2.getY()) / 2f;
            cy = Math.abs(mech1.getY() + mech2.getY()) / 2f;
            camera.position.x = cx;
            camera.position.y = cy;
            firstRun = false;
        }

        System.out.println("Target:" + targetZoom);
        camera.zoom = 1f + (targetZoom - 1f) * percent;
    }
}
