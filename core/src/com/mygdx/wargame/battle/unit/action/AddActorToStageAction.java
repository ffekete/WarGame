package com.mygdx.wargame.battle.unit.action;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.wargame.battle.map.render.IsometricTiledMapRendererWithSprites;

public class AddActorToStageAction extends Action {

    private Stage stage;
    private Actor actor;

    public AddActorToStageAction(Stage stage, Actor actor) {
        this.stage = stage;
        this.actor = actor;
    }

    @Override
    public boolean act(float delta) {
        stage.addActor(actor);
        return true;
    }
}
