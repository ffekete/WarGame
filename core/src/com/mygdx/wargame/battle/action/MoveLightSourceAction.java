package com.mygdx.wargame.battle.action;

import box2dLight.Light;
import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;

public class MoveLightSourceAction extends TemporalAction {

    private boolean firstRun = true;
    private float startX, startY, endX, endY;
    boolean finished = false;

    private Light light;
    private RayHandler rayHandler;
    private Color color;

    public MoveLightSourceAction(float endX, float endY, float duration, float startX, float startY, RayHandler rayHandler, Color color) {
        this.endX = endX;
        this.endY = endY;
        this.startX = startX;
        this.startY = startY;
        this.rayHandler = rayHandler;
        setDuration(duration);
        this.color = color;
    }

    @Override
    protected void update(float percent) {
        float x, y;
        if (firstRun) {
            light = new PointLight(rayHandler, 8, color, 1f, startX, startY);
            x = startX;
            y = startY;
            //actionLock.setLocked(true);
            firstRun = false;
        } else if (percent >= 0.95 && !finished) {
            x = endX;
            y = endY;
            //actionLock.setLocked(false);
            finished = true;
            light.remove();
        } else {
            x = startX + (endX - startX) * percent;
            y = startY + (endY - startY) * percent;
        }

        light.setPosition(x, y);

    }
}
