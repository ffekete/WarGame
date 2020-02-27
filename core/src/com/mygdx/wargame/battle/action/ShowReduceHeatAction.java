package com.mygdx.wargame.battle.action;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;

public class ShowReduceHeatAction extends TemporalAction {

    private float actual, target;
    private ProgressBar progressBar;

    public ShowReduceHeatAction(float actual, float target, ProgressBar progressBar) {
        this.actual = actual;
        this.target = target;
        this.progressBar = progressBar;
        this.setDuration(1);
    }

    @Override
    protected void update(float percent) {
        progressBar.setValue((actual - (actual - target) * percent));
    }
}
