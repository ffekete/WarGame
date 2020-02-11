package com.mygdx.wargame.battle.unit.action;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class RemoveCustomActorAction extends Action {

    private Stage stage;
    private Actor actor;

    public RemoveCustomActorAction(Stage stage, Actor actor) {
        this.stage = stage;
        this.actor = actor;
    }

    @Override
    public boolean act(float delta) {
        stage.getActors().removeValue(actor, true);
        return true;
    }
}
