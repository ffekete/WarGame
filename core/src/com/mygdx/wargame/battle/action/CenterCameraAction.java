package com.mygdx.wargame.battle.action;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;

public class CenterCameraAction extends TemporalAction {

    private float startX;
    private float startY;
    private float endX;
    private float endY;
    private Camera camera;

    public CenterCameraAction(Camera camera) {
        this.camera = camera;
    }

    @Override
    protected void update(float percent) {
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
        camera.position.x = x;
        camera.position.y = y;
    }

    public void setStartPosition(float x, float y) {
        startX = x;
        startY = y;
    }

    public void setPosition(float x, float y) {
        endX = x;
        endY = y;
    }
}

