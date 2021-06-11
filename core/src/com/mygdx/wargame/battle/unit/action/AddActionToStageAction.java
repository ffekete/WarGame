package com.mygdx.wargame.battle.unit.action;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class AddActionToStageAction extends Action {

    private Stage stage;
    private Action action;

    public AddActionToStageAction(Stage stage, Action action) {
        this.stage = stage;
        this.action = action;
    }

    @Override
    public boolean act(float delta) {
        this.stage.addAction(action);
        return true;
    }
}
