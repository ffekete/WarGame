package com.mygdx.wargame.battle.unit.action;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Pool;

public class RemoveCustomActorAction extends Action {

    private Stage stage;
    private Actor actor;
    private Group group;
    private Pool pool;

    public RemoveCustomActorAction(Stage stage, Actor actor, Pool pool) {
        this.stage = stage;
        this.actor = actor;
        this.pool = pool;
    }

    public RemoveCustomActorAction(Group group, Actor actor, Pool pool) {
        this.group = group;
        this.actor = actor;
        this.pool = pool;
    }

    @Override
    public boolean act(float delta) {
        if (stage != null)
            stage.getActors().removeValue(actor, true);
        else
            group.removeActor(actor, true);

        if(pool != null)
            pool.free(actor);
        return true;
    }
}
