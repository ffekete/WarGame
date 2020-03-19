package com.mygdx.wargame.battle.unit.action;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
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
        System.out.println("Adding actor " + actor);
        isometricTiledMapRendererWithSprites.addObject(actor);
        return true;
    }
}
