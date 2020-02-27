package com.mygdx.wargame.battle.unit.action;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class AddActorAction extends Action {

    private Stage stage;
    private Actor actor;
    private Group group;

    public AddActorAction(Stage stage, Actor actor) {
        this.stage = stage;
        this.actor = actor;
    }

    public AddActorAction(Group group, Actor actor) {
        this.group = group;
        this.actor = actor;
    }

    @Override
    public boolean act(float delta) {
        if (stage != null)
            stage.addActor(actor);
        else
            group.addActor(actor);
        return true;
    }
}
