package com.mygdx.wargame.battle.unit.action;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;

import java.util.Random;

public class ShakeAction extends TemporalAction {

    private Actor actor;

    private float origX, origY;

    private float offSetX = 0, offSetY = 0;

    private int step = 0;


    public ShakeAction(float duration, Actor actor) {
        super(duration);
        this.actor = actor;
        this.origX = actor.getX();
        this.origY = actor.getY();
    }

    @Override
    protected void update(float percent) {
        if(percent < 0.9999f) {

            if(step ==0) {

                offSetX = origX + new Random().nextFloat() / 50;
                offSetY = origY + new Random().nextFloat() / 20;

                actor.setPosition(offSetX, offSetY);
            }
            step = (step + 1) % 3;

        } else {
            actor.setPosition(origX, origY);
        }
    }
}
