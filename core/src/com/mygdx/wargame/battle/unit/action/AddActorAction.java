package com.mygdx.wargame.battle.unit.action;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.wargame.battle.map.render.IsometricTiledMapRendererWithSprites;

public class AddActorAction extends Action {

    private IsometricTiledMapRendererWithSprites isometricTiledMapRendererWithSprites;
    private Actor actor;

    public AddActorAction(IsometricTiledMapRendererWithSprites isometricTiledMapRendererWithSprites, Actor actor) {
        this.isometricTiledMapRendererWithSprites = isometricTiledMapRendererWithSprites;
        this.actor = actor;
    }

    @Override
    public boolean act(float delta) {
        isometricTiledMapRendererWithSprites.addObject(actor);
        return true;
    }
}
