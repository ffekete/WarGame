package com.mygdx.wargame.battle.action;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.mygdx.wargame.battle.map.decoration.SelectionMarker;
import com.mygdx.wargame.battle.map.render.IsometricTiledMapRendererWithSprites;

public class RemoveSelectionMarkerAction extends Action {

    private IsometricTiledMapRendererWithSprites isometricTiledMapRendererWithSprites;

    public RemoveSelectionMarkerAction(IsometricTiledMapRendererWithSprites isometricTiledMapRendererWithSprites) {
        this.isometricTiledMapRendererWithSprites = isometricTiledMapRendererWithSprites;
    }

    @Override
    public boolean act(float delta) {
        isometricTiledMapRendererWithSprites.removeAll(SelectionMarker.class);
        return true;
    }
}
