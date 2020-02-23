package com.mygdx.wargame.battle.action;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.mygdx.wargame.battle.screen.StageElementsStorage;

public class ZoomToNormalCameraAction extends TemporalAction {

    private OrthographicCamera camera;
    private float origZoom;


    public ZoomToNormalCameraAction(OrthographicCamera camera) {
        this.camera = camera;
        setDuration(2);
        origZoom = camera.zoom;
    }

    @Override
    protected void update(float percent) {
        camera.zoom = origZoom - (origZoom - 1) * percent;
    }
}
