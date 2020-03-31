package com.mygdx.wargame.battle.unit.action;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Pool;
import com.mygdx.wargame.battle.map.render.IsometricTiledMapRendererWithSprites;

public class RemoveCustomActorAction extends Action {

    private IsometricTiledMapRendererWithSprites isometricTiledMapRendererWithSprites;
    private Actor actor;
    private Pool pool;

    public RemoveCustomActorAction(IsometricTiledMapRendererWithSprites isometricTiledMapRendererWithSprites, Actor actor, Pool pool) {
        this.isometricTiledMapRendererWithSprites = isometricTiledMapRendererWithSprites;
        this.actor = actor;
        this.pool = pool;
    }

    @Override
    public boolean act(float delta) {

        isometricTiledMapRendererWithSprites.removeObject(actor);

        if (pool != null)
            pool.free(actor);
        return true;
    }
}
