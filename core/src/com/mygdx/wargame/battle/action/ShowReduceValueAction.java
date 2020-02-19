package com.mygdx.wargame.battle.action;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;

public class ShowReduceValueAction extends TemporalAction {

    private float actual, target;
    private ProgressBar progressBar;
    private float sign = 0f;

    public ShowReduceValueAction(float actual, float target, ProgressBar progressBar) {
        this.actual = actual;
        this.target = target;
        this.progressBar = progressBar;
        this.setDuration(1);
        this.sign = target >= actual ? 1 : -1;

    }

    @Override
    protected void update(float percent) {
        progressBar.setValue((actual + sign *( (actual -target) * percent)));
    }
}
